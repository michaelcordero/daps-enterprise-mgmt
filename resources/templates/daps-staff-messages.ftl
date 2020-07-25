<#-- @ftlvariable name="presenter" type="presenters.WebDAPSStaffMessagesPresenter" -->
<#import "dashboard-ui.ftl" as ui />
<@ui.dashboardUI title="DAPS-Staff-Messages">
    <link rel="stylesheet" href="${presenter.theme.css}">
    <main class="content">
        <div class="header">
            <h1 class="header-title">
                DAPS Staff Messages
            </h1>
        </div>
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <table id="data-daps_staff_messages"
                               class="table table-striped table-bordered table-responsive table-hover"
                               style="width: 100%;">
                            <thead>
                            <tr>
                                <th>Memo Date</th>
                                <th>Entered By</th>
                                <th>Intended For</th>
                                <th>Message</th>
                                <th>Staff Messages Key</th>
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
            const web_socket = new WebSocket('ws://localhost:8080/update', ['http'])
            // Realtime Update Event
            web_socket.onmessage = function (event) {
                // Additional server logic prevents this from locally updating which would be redundant.
                if (event.data === 'daps_staff_messages') {
                    table.ajax.reload(null, false)
                }
            }
            // Datatables Editor
            const editor = new $.fn.dataTable.Editor({
                ajax: {
                    // CRUD
                    create: {
                        // Defaults
                        contentType: 'application/json',
                        dataType: "json",
                        type: 'POST',
                        url: '/web/daps_staff_messages',
                        "data": function (d) {
                            // removing row key
                            const raw = JSON.stringify(d.data);
                            return raw.substr(raw.indexOf(':') + 1)
                        },
                        success: function () {
                            web_socket.send("daps_staff_messages")
                        }
                    },
                    edit: {
                        // Defaults
                        contentType: 'application/json',
                        dataType: "json",
                        type: 'PUT',
                        url: '/web/daps_staff_messages',
                        "data": function (d) {
                            // removing row key
                            const raw = JSON.stringify(d.data);
                            return raw.substr(raw.indexOf(':') + 1)
                        },
                        success: function () {
                            web_socket.send("daps_staff_messages")
                        }
                    },
                    remove: {
                        // Defaults
                        contentType: 'application/json',
                        dataType: "json",
                        type: 'DELETE',
                        url: '/web/daps_staff_messages',
                        "deleteBody": false,
                        "data": function (d) {
                            // removing row key
                            const raw = JSON.stringify(d.data);
                            return raw.substr(raw.indexOf(':') + 1)
                        },
                        success: function () {
                            web_socket.send("daps_staff_messages")
                        },
                    },
                },
                table: '#data-daps_staff_messages',
                idSrc: 'staff_messages_key',
                fields: [
                    {label: 'Memo Date', name: 'memo_date'},
                    {label: 'Entered By', name: 'entered_by'},
                    {label: 'Intended', name: 'intended_for'},
                    {label: 'Message', name: 'message'},
                    {label: 'Staff Messages Key', name: 'staff_messages_key'},
                ]
            });
            // Primary Key
            editor.disable('staff_messages_key')
            // Datatables basic
            const table = $('#data-daps_staff_messages').DataTable({
                "ajax": {
                    "url": '/web/daps_staff_messages',
                    "dataSrc": ''
                },
                dom: 'lBfrtip',
                "autoWidth": true,
                columns:
                    [
                        {data: "memo_date"},
                        {data: "entered_by"},
                        {data: "intended_for"},
                        {data: "message","width": "75%"},
                        {data: "staff_messages_key"}
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
