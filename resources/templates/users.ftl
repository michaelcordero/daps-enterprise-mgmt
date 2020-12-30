<#-- @ftlvariable name="presenter" type="presenters.WebUsersPresenter" -->
<#import "dashboard-ui.ftl" as ui />
<@ui.dashboardUI title="Users">
    <link rel="stylesheet" href="${presenter.theme.css}">
    <main class="content">
        <div class="header">
            <h1 class="header-title">
                Users
            </h1>
        </div>
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <table id="data-users" class="table table-striped table-bordered table-responsive table-hover" style="width: 100%;">
                            <thead>
                            <tr>
                                <th>Id</th>
                                <th>First Name</th>
                                <th>Last Name</th>
<#--                                <th>Password</th>-->
                                <th>Email </th>
                                <th>Role</th>
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
                if(event_data['UPDATE'] === 'USERS') {
                    table.ajax.reload(null, false)
                } else if (event_data['ALERT'] === 'USERS') {
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
                        url: '/web/users',
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
                        url: '/web/users',
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
                        url: '/web/users',
                        "deleteBody": false,
                        "data": function (d) {
                            // removing row key
                            const raw = JSON.stringify(d.data);
                            return raw.substr(raw.indexOf(':') + 1)
                        },
                    },
                },
                table: '#data-users',
                idSrc: 'id',
                fields: [
                    {label: 'Id', name: 'id'},
                    {label: 'First Name', name: 'first_name'},
                    {label: 'Last Name', name: 'last_name'},
                    {label: 'Password', name: 'passwordHash', type: 'password'},
                    {label: 'Email', name: 'email'},
                    {label: 'Role', name: 'role'},
                ]
            });
            // Primary Key
            editor.disable('id')
            // Datatables basic
            const table = $('#data-users').DataTable({
                "ajax": {
                    "url": '/web/users',
                    "dataSrc": ''
                },
                dom: 'lBfrtip',
                "autoWidth": true,
                columns:
                    [
                        {data: "id"},
                        {data: "first_name"},
                        {data: "last_name"},
                        // {data: "passwordHash"},
                        {data: "email"},
                        {data: "role"},
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
