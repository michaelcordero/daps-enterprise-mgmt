<#-- @ftlvariable name="presenter" type="presenters.WebWorkOrdersPresenter" -->
<#import "dashboard-ui.ftl" as ui />
<@ui.dashboardUI title="Work Orders">
    <link rel="stylesheet" href="${presenter.theme.css}">
    <main class="content">
        <div class="header">
            <h1 class="header-title">
                Work Orders
            </h1>
        </div>
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <table id="data-work-orders"
                               class="table table-striped table-bordered table-responsive table-hover"
                               style="width: 100%;">
                            <thead>
                            <tr>
                                <th>Work Order #</th>
                                <th>Client#</th>
                                <th>Emp Num#</th>
                                <th>Temp Perm</th>
                                <th>Filled Date</th>
                                <th>Filled Rate</th>
                                <th>Start Date</th>
                                <th>Start Time</th>
                                <th>End Time</th>
                                <th>Services Category</th>
                                <th>Job Description</th>
                                <th>Skills Required</th>
                                <th>Work Hours</th>
                                <th>Will Train</th>
                                <th>Confidential</th>
                                <th>Contact Name</th>
                                <th>Fees Discussed</th>
                                <th>Note</th>
                                <th>Entered By</th>
                                <th>Entered Date</th>
                                <th>Post</th>
                                <th>Active</th>
                                <th>Left Message</th>
                                <th>Confirmed</th>
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
                if(event_data['UPDATE'] === 'WORK_ORDERS') {
                    table.ajax.reload(null, false)
                } else if (event_data['ALERT'] === 'WORK_ORDERS') {
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
                        url: '/web/work_orders',
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
                        url: '/web/work_orders',
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
                        url: '/web/work_orders',
                        "deleteBody": false,
                        "data": function (d) {
                            // removing row key
                            const raw = JSON.stringify(d.data);
                            return raw.substr(raw.indexOf(':') + 1)
                        },
                    },
                },
                table: '#data-work-orders',
                idSrc: 'wo_number',
                fields: [
                    {label: 'Work Order#', name: 'wo_number'},
                    {label: 'Client #', name: 'client_num'},
                    {label: 'Emp #', name: 'emp_num'},
                    {label: 'Temp Perm', name: 'temp_perm'},
                    {label: 'Filled Date', name: 'filled_date'},
                    {label: 'Filled Rate', name: 'filled_rate'},
                    {label: 'Start Date', name: 'start_date'},
                    {label: 'Start Time', name: 'start_time'},
                    {label: 'End Time', name: 'end_time'},
                    {label: 'Services Category', name: 'services_category'},
                    {label: 'Job Description', name: 'job_description'},
                    {label: 'Skills Required', name: 'skills_required'},
                    {label: 'Work Hours', name: 'work_hours'},
                    {label: 'Will Train', name: 'will_train'},
                    {label: 'Confidential', name: 'confidential'},
                    {label: 'Contact Name', name: 'contact_name'},
                    {label: 'Fees Discussed', name: 'fees_discussed'},
                    {label: 'Note', name: 'note'},
                    {label: 'Entered By', name: 'entered_by'},
                    {label: 'Entered Date', name: 'entered_date'},
                    {label: 'Post', name: 'post'},
                    {label: 'Active', name: 'active'},
                    {label: 'Left Message', name: 'left_message'},
                    {label: 'Confirmed', name: 'confirmed'},
                ]
            });
            editor.disable('wo_number')
            // Datatables basic
            const table = $('#data-work-orders').DataTable({
                "ajax": {
                    "url": '/web/work_orders',
                    "dataSrc": ''
                },
                dom: 'lBfrtip',
                "autoWidth": true,
                columns:
                    [
                        {data: 'wo_number'},
                        {data: 'client_num'},
                        {data: 'emp_num'},
                        {data: 'temp_perm'},
                        {data: 'filled_date'},
                        {data: 'filled_rate'},
                        {data: 'start_date'},
                        {data: 'start_time'},
                        {data: 'end_time'},
                        {data: 'services_category'},
                        {data: 'job_description'},
                        {data: 'skills_required'},
                        {data: 'work_hours'},
                        {data: 'will_train'},
                        {data: 'confidential'},
                        {data: 'contact_name'},
                        {data: 'fees_discussed'},
                        {data: 'note'},
                        {data: 'entered_by'},
                        {data: 'entered_date'},
                        {data: 'post'},
                        {data: 'active'},
                        {data: 'left_message'},
                        {data: 'confirmed'},
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
