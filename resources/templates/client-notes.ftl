<#-- @ftlvariable name="presenter" type="presenters.WebClientNotesPresenter" -->
<#import "dashboard-ui.ftl" as ui />
<@ui.dashboardUI title="ClientNotes">
    <link rel="stylesheet" href="${presenter.theme.css}">
    <main class="content">
        <div class="header">
            <h1 class="header-title">
                Client Notes
            </h1>
        </div>
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <table id="data-client-notes"
                               class="table table-striped table-bordered table-responsive table-hover"
                               style="width: 100%;">
                            <thead>
                            <tr>
                                <th>Client#</th>
                                <th>Note Date</th>
                                <th>Initial</th>
                                <th>Note</th>
                                <th>Client Note Key</th>
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
                if (event.data === 'client_notes') {
                    table.ajax.reload(null, false)
                } else if (event.data === 'alert:client_notes') {
                    alert('Data save change failed. Reverting data.')
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
                        url: '/web/client_notes',
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
                        url: '/web/client_notes',
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
                        url: '/web/client_notes',
                        "deleteBody": false,
                        "data": function (d) {
                            // removing row key
                            const raw = JSON.stringify(d.data);
                            return raw.substr(raw.indexOf(':') + 1)
                        },
                    },
                },
                table: '#data-client-notes',
                idSrc: 'client_note_key',
                fields: [
                    {label: 'Client #', name: 'client_num'},
                    {label: 'Note Date', name: 'notedate'},
                    {label: 'Initial', name: 'initial'},
                    {label: 'Note', name: 'note'},
                    {label: 'Client Note Key', name: 'client_note_key'},
                ]
            });
            editor.disable('client_note_key')
            editor.on('initSubmit', function (e, action) {
                if ( !editor.field('client_num').val() || isNaN(editor.field('client_num').val())){
                    editor.field('client_num').error('client # must be a number')
                    return false;
                }
            })
            // Datatables basic
            const table = $('#data-client-notes').DataTable({
                "ajax": {
                    "url": '/web/client_notes',
                    "dataSrc": ''
                },
                dom: 'lBfrtip',
                "autoWidth": true,
                columns:
                    [
                        {data: "client_num"},
                        {data: "notedate"},
                        {data: "initial"},
                        {data: "note","width": "75%"},
                        {data: "client_note_key"}
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
