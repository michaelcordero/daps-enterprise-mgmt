<#-- @ftlvariable name="presenter" type="presenters.WebPermReqNotesPresenter" -->
<#import "dashboard-ui.ftl" as ui />
<@ui.dashboardUI title="Perm Req Notes">
    <link rel="stylesheet" href="${presenter.theme.css}">
    <main class="content">
        <div class="header">
            <h1 class="header-title">
                Perm Req Notes
            </h1>
        </div>
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <table id="data-perm_req_notes"
                               class="table table-striped table-bordered table-responsive table-hover"
                               style="width: 100%;">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Employee #</th>
                                <th>Desired Location</th>
                                <th>Start Date</th>
                                <th>Full Time</th>
                                <th>Desired Days</th>
                                <th>Special Requests</th>
                                <th>Not Interested</th>
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
                if (event.data === 'perm_req_notes') {
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
                        url: '/web/perm_req_notes',
                        "data": function (d) {
                            // removing row key
                            const raw = JSON.stringify(d.data);
                            return raw.substr(raw.indexOf(':') + 1)
                        },
                        success: function () {
                            web_socket.send("perm_req_notes")
                        }
                    },
                    edit: {
                        // Defaults
                        contentType: 'application/json',
                        dataType: "json",
                        type: 'PUT',
                        url: '/web/perm_req_notes',
                        "data": function (d) {
                            // removing row key
                            const raw = JSON.stringify(d.data);
                            return raw.substr(raw.indexOf(':') + 1)
                        },
                        success: function () {
                            web_socket.send("perm_req_notes")
                        }
                    },
                    remove: {
                        // Defaults
                        contentType: 'application/json',
                        dataType: "json",
                        type: 'DELETE',
                        url: '/web/perm_req_notes',
                        "deleteBody": false,
                        "data": function (d) {
                            // removing row key
                            const raw = JSON.stringify(d.data);
                            return raw.substr(raw.indexOf(':') + 1)
                        },
                        success: function () {
                            web_socket.send("perm_req_notes")
                        },
                    },
                },
                table: '#data-perm_req_notes',
                idSrc: 'id',
                fields: [
                    {label: 'ID', name: 'id'},
                    {label: 'Employee #', name: 'emp_num'},
                    {label: 'Desired Location', name: 'desired_location'},
                    {label: 'Start Date', name: 'start_date'},
                    {label: 'Full Time', name: 'fulltime'},
                    {label: 'Desired Days', name: 'desired_days'},
                    {label: 'Special Requests', name: 'special_requests'},
                    {label: 'Not Interested', name: 'not_interested'},
                ]
            });
            // Primary Key
            editor.disable('id')
            editor.on('initSubmit', function (e, action) {
                if ( !editor.field('emp_num').val() || isNaN(editor.field('emp_num').val())){
                    editor.field('emp_num').error('Employee # must be a number')
                    return false;
                }
            })
            // Datatables basic
            const table = $('#data-perm_req_notes').DataTable({
                "ajax": {
                    "url": '/web/perm_req_notes',
                    "dataSrc": ''
                },
                dom: 'lBfrtip',
                "autoWidth": true,
                columns:
                    [
                        {data: "id"},
                        {data: "emp_num"},
                        {data: "desired_location"},
                        {data: "start_date"},
                        {data: "fulltime"},
                        {data: "desired_days"},
                        {data: "special_requests"},
                        {data: "not_interested"}
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
