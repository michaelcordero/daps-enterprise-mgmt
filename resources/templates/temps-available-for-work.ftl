<#-- @ftlvariable name="presenter" type="presenters.WebTempsAvailableForWorkPresenter" -->
<#import "dashboard-ui.ftl" as ui />
<@ui.dashboardUI title="TempsAvailableForWork">
    <link rel="stylesheet" href="${presenter.theme.css}">
    <main class="content">
        <div class="header">
            <h1 class="header-title">
                Temps Available For Work
            </h1>
        </div>
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <table id="data-temps-available-for-work"
                               class="table table-striped table-bordered table-responsive table-hover"
                               style="width: 100%;">
                            <thead>
                            <tr>
                                <th>Rec#</th>
                                <th>Emp#</th>
                                <th>Date Can Work</th>
                            </tr>
                            </thead>
                            <tfoot>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <script>
        $(function () {
            const web_socket = new WebSocket('ws://${presenter.host+':'+presenter.port}/update', ['http'])
            // Realtime Update Event
            web_socket.onmessage = function(event) {
                // Expected server message
                let event_data = JSON.parse(event.data)
                if(event_data['UPDATE'] === 'TEMPS_AVAILABLE_FOR_WORK') {
                    table.ajax.reload(null, false)
                } else if (event_data['ALERT'] === 'TEMPS_AVAILABLE_FOR_WORK') {
                    alert(`Data save failed. Reverting data. \nCause: `+event_data['ERROR'])
                    table.ajax.reload(null, false)
                }
            };
            // Datatables Editor
            const editor = new $.fn.dataTable.Editor({
                ajax: {
                    // CRUD
                    create: {
                        // Defaults
                        contentType: 'application/json',
                        dataType: "json",
                        type: 'POST',
                        url: '/web/temps_available_for_work',
                        "data": function (d) {
                            // removing row key
                            const raw = JSON.stringify(d.data);
                            return raw.substr(raw.indexOf(':') + 1)
                        },
                    },
                    edit: {
                        // Defaults
                        contentType: 'application/json',
                        dataType: "json",
                        type: 'PUT',
                        url: '/web/temps_available_for_work',
                        "data": function (d) {
                            // removing row key
                            const raw = JSON.stringify(d.data);
                            return raw.substr(raw.indexOf(':') + 1)
                        },
                    },
                    remove: {
                        // Defaults
                        contentType: 'application/json',
                        dataType: "json",
                        type: 'DELETE',
                        url: '/web/temps_available_for_work',
                        "deleteBody": false,
                        "data": function (d) {
                            // removing row key
                            const raw = JSON.stringify(d.data);
                            return raw.substr(raw.indexOf(':') + 1)
                        },
                    },
                },
                table: '#data-temps-available-for-work',
                idSrc: 'rec_num',
                fields: [
                    {label: 'Rec#', name: 'rec_num'},
                    {label: 'Employee#', name: 'emp_num'},
                    {label: 'Date Can Work', name: 'date_can_work'},
                ]
            });
            editor.disable('rec_num')
            // Datatables basic
            const table = $('#data-temps-available-for-work').DataTable({
                "ajax": {
                    "url": '/web/temps_available_for_work',
                    "dataSrc": ''
                },
                dom: 'lBfrtip',
                "autoWidth": true,
                columns:
                    [
                        {data: "rec_num"},
                        {data: "emp_num"},
                        {data: "date_can_work"},
                    ],
                select: true,
                buttons: [
                    {extend: 'create', editor: editor},
                    {extend: 'edit', editor: editor},
                    {extend: 'remove', editor: editor}
                ]
            });
            // Double click for edit
            table.on('dblclick', 'tr', function () {
                table.row(this).edit()
            });

            // Submit if all changed
            table.on('click', 'tbody td:not(:first-child)', function () {
                editor.inline(this, {
                    submit: 'all'
                });
            });
        });
    </script>
</@ui.dashboardUI>
