<#-- @ftlvariable name="presenter" type="presenters.WebInterviewGuidePresenter" -->
<#import "dashboard-ui.ftl" as ui />
<@ui.dashboardUI title="Interview Guides">
    <link rel="stylesheet" href="${presenter.theme.css}">
    <main class="content">
        <div class="header">
            <h1 class="header-title">
                Interview Guides
            </h1>
        </div>
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <table id="data-interview_guides"
                               class="table table-striped table-bordered table-responsive table-hover"
                               style="width: 100%;">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Client #</th>
                                <th>Client Contact</th>
                                <th>Employee #</th>
                                <th>Employee Name</th>
                                <th>Referral Date</th>
                                <th>Referral Notes</th>
                                <th>Interview Complete</th>
                                <th>Interview Notes</th>
                                <th>Work Order #</th>
                                <th>Employee Notes ID</th>
                                <th>Client Notes ID</th>
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
                if (event.data === 'interview_guides') {
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
                        url: '/web/interview_guides',
                        "data": function (d) {
                            // removing row key
                            const raw = JSON.stringify(d.data);
                            return raw.substr(raw.indexOf(':') + 1)
                        },
                        success: function () {
                            web_socket.send("interview_guides")
                        }
                    },
                    edit: {
                        // Defaults
                        contentType: 'application/json',
                        dataType: "json",
                        type: 'PUT',
                        url: '/web/interview_guides',
                        "data": function (d) {
                            // removing row key
                            const raw = JSON.stringify(d.data);
                            return raw.substr(raw.indexOf(':') + 1)
                        },
                        success: function () {
                            web_socket.send("interview_guides")
                        }
                    },
                    remove: {
                        // Defaults
                        contentType: 'application/json',
                        dataType: "json",
                        type: 'DELETE',
                        url: '/web/interview_guides',
                        "deleteBody": false,
                        "data": function (d) {
                            // removing row key
                            const raw = JSON.stringify(d.data);
                            return raw.substr(raw.indexOf(':') + 1)
                        },
                        success: function () {
                            web_socket.send("interview_guides")
                        },
                    },
                },
                table: '#data-interview_guides',
                idSrc: 'id',
                fields: [
                    {label: 'ID', name: 'id'},
                    {label: 'Client #', name: 'client_num'},
                    {label: 'Client Contact', name: 'client_contact'},
                    {label: 'Employee #', name: 'employee_num'},
                    {label: 'Employee Name', name: 'employee_name'},
                    {label: 'Referral Date', name: 'referral_date'},
                    {label: 'Referral Notes', name: 'referral_notes'},
                    {label: 'Interview Complete', name: 'interview_complete'},
                    {label: 'Interview Notes', name: 'interview_notes'},
                    {label: 'Work Order #', name: 'wo_number'},
                    {label: 'Employee Notes ID', name: 'emp_notes_id'},
                    {label: 'Client Notes ID', name: 'client_notes_id'},
                ]
            });
            // Primary Key
            editor.disable('id')
            editor.on('initSubmit', function (e, action) {
                if ( !editor.field('client_num').val() || isNaN(editor.field('client_num').val())) {
                    editor.field('client_num').error('Client # must be a number')
                    return false;
                }
                if ( !editor.field('employee_num').val() || isNaN(editor.field('employee_num').val())){
                    editor.field('employee_num').error('Employee # must be a number')
                    return false;
                }
            })
            // Datatables basic
            const table = $('#data-interview_guides').DataTable({
                "ajax": {
                    "url": '/web/interview_guides',
                    "dataSrc": ''
                },
                dom: 'lBfrtip',
                "autoWidth": true,
                columns:
                    [
                        {data: 'id'},
                        {data: 'client_num'},
                        {data: 'client_contact'},
                        {data: 'employee_num'},
                        {data: 'employee_name'},
                        {data: 'referral_date'},
                        {data: 'referral_notes'},
                        {data: 'interview_complete'},
                        {data: 'interview_notes'},
                        {data: 'wo_number'},
                        {data: 'emp_notes_id'},
                        {data: 'client_notes_id'},
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
