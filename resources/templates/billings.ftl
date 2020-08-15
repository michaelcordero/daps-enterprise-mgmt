<#-- @ftlvariable name="presenter" type="presenters.WebBillingsPresenter" -->
<#import "dashboard-ui.ftl" as ui />
<@ui.dashboardUI title="Billings">
    <link rel="stylesheet" href="${presenter.theme.css}">
    <main class="content">
        <div class="header">
            <h1 class="header-title">
                Billings
            </h1>
        </div>
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <table id="data-billings"
                               class="table table-striped table-bordered table-responsive table-hover"
                               style="width: 100%;">
                            <thead>
                            <tr>
                                <th>Counter</th>
                                <th>Client#</th>
                                <th>Employee#</th>
                                <th>Wdate</th>
                                <th>Hours</th>
                                <th>Start Time</th>
                                <th>End Time</th>
                                <th>DAPS Fee</th>
                                <th>Total Fee</th>
                                <th>Work Type</th>
                                <th>Work Order #</th>
                                <th>Open</th>
                                <th>Pmt1</th>
                                <th>Apamt1</th>
                                <th>Pmt2</th>
                                <th>Apamt2</th>
                                <th>Notesp</th>
                                <th>Pending</th>
                                <th>Assigned Date</th>
                                <th>Assigned By</th>
                                <th>Service Category</th>
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
                if (event.data === 'billings') {
                    table.ajax.reload(null, false)
                } else if (event.data === 'alert:billings') {
                    alert('Data save change failed. Reverting data.')
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
                        url: '/web/billings',
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
                        url: '/web/billings',
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
                        url: '/web/billings',
                        "deleteBody": false,
                        "data": function (d) {
                            // removing row key
                            const raw = JSON.stringify(d.data);
                            return raw.substr(raw.indexOf(':') + 1)
                        },
                    },
                },
                table: '#data-billings',
                idSrc: 'counter',
                fields: [
                    {label: 'Counter #', name: 'counter'},
                    {label: 'Client #', name: 'client_num', attr: {required: true}},
                    {label: 'Employee #', name: 'employee_num', attr: {required: true}},
                    {label: 'Wdate', name: 'wdate'},
                    {label: 'Hours', name: 'hours', attr: {required: true}},
                    {label: 'Start Time', name: 'start_time'},
                    {label: 'End Time', name: 'end_time'},
                    {label: 'DAPS Fee', name: 'daps_fee'},
                    {label: 'Total Fee', name: 'total_fee'},
                    {label: 'Work Type', name: 'worktype'},
                    {label: 'Work Order #', name: 'work_order_num'},
                    {label: 'Open', name: 'open'},
                    {label: 'Pmt 1', name: 'pmt1'},
                    {label: 'Apamt 1', name: 'apamt1'},
                    {label: 'Pmt 2', name: 'pmt2'},
                    {label: 'Apamt 2', name: 'apamt2'},
                    {label: 'NotesP', name: 'notesp'},
                    {label: 'Pending', name: 'pending'},
                    {label: 'Assigned Date', name: 'assigned_date'},
                    {label: 'Assigned By', name: 'assigned_by'},
                    {label: 'Service Category', name: 'service_category'}
                ]
            });
            editor.disable('counter')
            editor.on('initSubmit', function (e, action) {
                if ( !editor.field('client_num').val() || isNaN(editor.field('client_num').val())) {
                    editor.field('client_num').error('Client # must be a number')
                    return false;
                }
                if ( !editor.field('employee_num').val() || isNaN(editor.field('employee_num').val())){
                    editor.field('employee_num').error('Employee # must be a number')
                    return false;
                }
                if ( !editor.field('hours').val() || isNaN(editor.field('hours').val())){
                    editor.field('hours').error('Hours must be a number')
                    return false;
                }
            })
            // Datatables basic
            const table = $('#data-billings').DataTable({
                "ajax": {
                    "url": '/web/billings',
                    "dataSrc": ''
                },
                dom: 'lBfrtip',
                columns:
                    [
                        {data: 'counter'},
                        {data: 'client_num'},
                        {data: 'employee_num'},
                        {data: 'wdate'},
                        {data: 'hours'},
                        {data: 'start_time'},
                        {data: 'end_time'},
                        {data: 'daps_fee'},
                        {data: 'total_fee'},
                        {data: 'worktype'},
                        {data: 'work_order_num'},
                        {data: 'open'},
                        {data: 'pmt1'},
                        {data: 'apamt1'},
                        {data: 'pmt2'},
                        {data: 'apamt2'},
                        {data: 'notesp'},
                        {data: 'pending'},
                        {data: 'assigned_date'},
                        {data: 'assigned_by'},
                        {data: 'service_category'},
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
