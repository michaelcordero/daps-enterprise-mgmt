<#-- @ftlvariable name="presenter" type="presenters.WebBillingsPresenter" -->
<#import "dashboard-ui.ftl" as ui />
<@ui.dashboardUI title="Billings">
    <link rel="stylesheet" href="${presenter.theme.css}" >
    <div class="row">
        <div class="col">
            <div class="card">
                <div class="card-body">
                    <table id="data-billings" class="table table-striped table-bordered table-responsive table-hover"
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
    <script>
        // Datatables basic
        $('#data-billings').DataTable({
            "ajax": {
                "url": '/web/billings',
                "dataSrc": ''
            },
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
                ]
        });
    </script>
</@ui.dashboardUI>
