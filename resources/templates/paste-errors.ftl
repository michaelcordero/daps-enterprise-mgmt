<#-- @ftlvariable name="presenter" type="presenters.WebPasteErrorsPresenter" -->
<#import "dashboard-ui.ftl" as ui />
<@ui.dashboardUI title="Paste Errors">
    <link rel="stylesheet" href="${presenter.theme.css}">
    <main class="content">
        <div class="header">
            <h1 class="header-title">
                Paste Errors
            </h1>
        </div>
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <table id="data-paste-errors"
                               class="table table-striped table-bordered table-responsive table-hover"
                               style="width: 100%;">
                            <thead>
                            <tr>
                                <th>Client#</th>
                                <th>Pmt Type</th>
                                <th>Ref #</th>
                                <th>Pmt Date</th>
                                <th>Amount</th>
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
                if(event_data['UPDATE'] === 'PASTE_ERRORS') {
                    table.ajax.reload(null, false)
                } else if (event_data['ALERT'] === 'PASTE_ERRORS') {
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
                        url: '/web/paste_errors',
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
                        url: '/web/paste_errors',
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
                        url: '/web/paste_errors',
                        "deleteBody": false,
                        "data": function (d) {
                            // removing row key
                            const raw = JSON.stringify(d.data);
                            return raw.substr(raw.indexOf(':') + 1)
                        },
                    },
                },
                table: '#data-paste-errors',
                idSrc: 'ref_num',
                fields: [
                    {label: 'Client#', name: 'client_num', attr: {required: true}},
                    {label: 'Pmt Type', name: 'pmt_type'},
                    {label: 'Ref #', name: 'ref_num', attr: {required: true}},
                    {label: 'Pmt Date', name: 'pmt_date'},
                    {label: 'Amount', name: 'amount'},
                ]
            });
            // Datatables basic
            const table = $('#data-paste-errors').DataTable({
                "ajax": {
                    "url": '/web/paste_errors',
                    "dataSrc": ''
                },
                dom: 'lBfrtip',
                "autoWidth": true,
                columns:
                    [
                        {data: 'client_num'},
                        {data: 'pmt_type'},
                        {data: 'ref_num'},
                        {data: 'pmt_date'},
                        {data: 'amount'},
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
