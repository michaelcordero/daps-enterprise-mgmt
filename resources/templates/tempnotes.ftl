<#-- @ftlvariable name="presenter" type="presenters.WebTempNotesPresenter" -->
<#import "dashboard-ui.ftl" as ui />
<@ui.dashboardUI title="TempNotes">
    <link rel="stylesheet" href="${presenter.theme.css}">
    <main class="content">
        <div class="header">
            <h1 class="header-title">
                Temp Notes
            </h1>
        </div>
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <table id="data-tempnotes"
                               class="table table-striped table-bordered table-responsive table-hover"
                               style="width: 100%;">
                            <thead>
                            <tr>
                                <th>Employee#</th>
                                <th>Note Date</th>
                                <th>Initial</th>
                                <th>Emp Note</th>
                                <th>Temp Note Key</th>
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
                if (event.data === 'tempnotes') {
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
                        url: '/web/tempnotes',
                        "data": function (d) {
                            // removing row key
                            const raw = JSON.stringify(d.data);
                            return raw.substr(raw.indexOf(':') + 1)
                        },
                        success: function () {
                            web_socket.send("tempnotes")
                        }
                    },
                    edit: {
                        // Defaults
                        contentType: 'application/json',
                        dataType: "json",
                        type: 'PUT',
                        url: '/web/tempnotes',
                        "data": function (d) {
                            // removing row key
                            const raw = JSON.stringify(d.data);
                            return raw.substr(raw.indexOf(':') + 1)
                        },
                        success: function () {
                            web_socket.send("tempnotes")
                        }
                    },
                    remove: {
                        // Defaults
                        contentType: 'application/json',
                        dataType: "json",
                        type: 'DELETE',
                        url: '/web/tempnotes',
                        "deleteBody": false,
                        "data": function (d) {
                            // removing row key
                            const raw = JSON.stringify(d.data);
                            return raw.substr(raw.indexOf(':') + 1)
                        },
                        success: function () {
                            web_socket.send("tempnotes")
                        },
                    },
                },
                table: '#data-tempnotes',
                idSrc: 'temp_note_key',
                fields: [
                    {label: 'Employee #', name: 'emp_num'},
                    {label: 'Note Date', name: 'note_date'},
                    {label: 'Initial', name: 'initial'},
                    {label: 'Employee Note', name: 'emp_note'},
                    {label: 'Temp Note Key', name: 'temp_note_key'},
                ]
            });
            editor.disable('temp_note_key')
            editor.on('initSubmit', function (e, action) {
                if ( !editor.field('emp_num').val() || isNaN(editor.field('emp_num').val())){
                    editor.field('emp_num').error('Employee # must be a number')
                    return false;
                }
            })
            // Datatables basic
            const table = $('#data-tempnotes').DataTable({
                "ajax": {
                    "url": '/web/tempnotes',
                    "dataSrc": ''
                },
                dom: 'lBfrtip',
                "autoWidth": true,
                columns:
                    [
                        {data: "emp_num"},
                        {data: "note_date"},
                        {data: "initial"},
                        {data: "emp_note","width": "75%"},
                        {data: "temp_note_key"}
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
