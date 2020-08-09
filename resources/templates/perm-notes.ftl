<#-- @ftlvariable name="presenter" type="presenters.WebPermNotesPresenter" -->
<#import "dashboard-ui.ftl" as ui />
<@ui.dashboardUI title="PermNotes">
    <link rel="stylesheet" href="${presenter.theme.css}">
    <main class="content">
        <div class="header">
            <h1 class="header-title">
                Perm Notes
            </h1>
        </div>
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <table id="data-perm_notes"
                               class="table table-striped table-bordered table-responsive table-hover"
                               style="width: 100%;">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Employee #</th>
                                <th>Note Date</th>
                                <th>Initial</th>
                                <th>Comments</th>
                                <th>Follow Update</th>
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
            web_socket.onmessage = function (event) {
                // Additional server logic prevents this from locally updating which would be redundant.
                if (event.data === 'perm_notes') {
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
                        url: '/web/perm_notes',
                        "data": function (d) {
                            // removing row key
                            const raw = JSON.stringify(d.data);
                            return raw.substr(raw.indexOf(':') + 1)
                        },
                        success: function () {
                            web_socket.send("perm_notes")
                        }
                    },
                    edit: {
                        // Defaults
                        contentType: 'application/json',
                        dataType: "json",
                        type: 'PUT',
                        url: '/web/perm_notes',
                        "data": function (d) {
                            // removing row key
                            const raw = JSON.stringify(d.data);
                            return raw.substr(raw.indexOf(':') + 1)
                        },
                        success: function () {
                            web_socket.send("perm_notes")
                        }
                    },
                    remove: {
                        // Defaults
                        contentType: 'application/json',
                        dataType: "json",
                        type: 'DELETE',
                        url: '/web/perm_notes',
                        "deleteBody": false,
                        "data": function (d) {
                            // removing row key
                            const raw = JSON.stringify(d.data);
                            return raw.substr(raw.indexOf(':') + 1)
                        },
                        success: function () {
                            web_socket.send("perm_notes")
                        },
                    },
                },
                table: '#data-perm_notes',
                idSrc: 'id',
                fields: [
                    {label: 'ID', name: 'id'},
                    {label: 'Employee #', name: 'emp_num'},
                    {label: 'Note Date', name: 'note_date'},
                    {label: 'Initial', name: 'initial'},
                    {label: 'Comments', name: 'comments'},
                    {label: 'Follow Update', name: 'follow_update'},
                ]
            });
            // Primary Key
            editor.disable('id')
            // Datatables basic
            const table = $('#data-perm_notes').DataTable({
                "ajax": {
                    "url": '/web/perm_notes',
                    "dataSrc": ''
                },
                dom: 'lBfrtip',
                "autoWidth": true,
                columns:
                    [
                        {data: "id"},
                        {data: "emp_num"},
                        {data: "note_date"},
                        {data: "initial"},
                        {data: "comments","width": "75%"},
                        {data: "follow_update"}
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
