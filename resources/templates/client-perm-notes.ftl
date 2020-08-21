<#-- @ftlvariable name="presenter" type="presenters.WebClientPermNotesPresenter" -->
<#import "dashboard-ui.ftl" as ui />
<@ui.dashboardUI title="ClientPermNotes">
    <link rel="stylesheet" href="${presenter.theme.css}">
    <main class="content">
        <div class="header">
            <h1 class="header-title">
                Client Perm Notes
            </h1>
        </div>
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <table id="data-client-perm-notes"
                               class="table table-striped table-bordered table-responsive table-hover"
                               style="width: 100%;">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Client#</th>
                                <th>Work Order#</th>
                                <th>Not Interested</th>
                                <th>Staff Name</th>
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
                if(event_data['UPDATE'] === 'CLIENT_PERM_NOTES') {
                    table.ajax.reload(null, false)
                } else if (event_data['ALERT'] === 'CLIENT_PERM_NOTES') {
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
                        url: '/web/client_perm_notes',
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
                        url: '/web/client_perm_notes',
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
                        url: '/web/client_perm_notes',
                        "deleteBody": false,
                        "data": function (d) {
                            // removing row key
                            const raw = JSON.stringify(d.data);
                            return raw.substr(raw.indexOf(':') + 1)
                        },
                    },
                },
                table: '#data-client-perm-notes',
                idSrc: 'id',
                fields: [
                    {label: 'ID', name: 'id'},
                    {label: 'Client #', name: 'client_num'},
                    {label: 'Work Order#', name: 'wo_num'},
                    {label: 'Not Interested', name: 'not_interested'},
                    {label: 'Staff Name', name: 'staff_name'},
                ]
            });
            editor.disable('id')
            // Datatables basic
            const table = $('#data-client-perm-notes').DataTable({
                "ajax": {
                    "url": '/web/client_perm_notes',
                    "dataSrc": ''
                },
                dom: 'lBfrtip',
                "autoWidth": true,
                columns:
                    [
                        {data: "id"},
                        {data: "client_num"},
                        {data: "wo_num"},
                        {data: "not_interested"},
                        {data: "staff_name"},
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
