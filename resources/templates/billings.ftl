<#-- @ftlvariable name="billings" type="java.util.List<model.Billing>" -->
<#-- @ftlvariable name="presenter" type="presenters.WebBillingsPresenter" -->
<#import "dashboard-ui.ftl" as ui />
<@ui.dashboardUI title="Billings">
    <div class="row">
        <div class="col">
            <div class="card">
                <div class="card-body">
                    <table id="datatables-basic" class="table table-striped table-bordered table-responsive " style="width: 100%;">
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
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list billings as billing>
                            <tr>
                                <th>${billing.counter!""}</th>
                                <td>${billing.client_num!""}</td>
                                <td>${billing.employee_num!""}</td>
                                <td>${billing.wdate!""}</td>
                                <td>${billing.hours!""}</td>
                                <td>${billing.start_time!""}</td>
                                <td>${billing.end_time!""}</td>
                                <td>${billing.daps_fee!""}</td>
                                <td>${billing.total_fee!""}</td>
                                <td>${billing.worktype!""}</td>
                                <td>${billing.work_order_num!""}</td>
                                <td>${billing.open?c}</td>
                                <td>${billing.pmt1!""}</td>
                                <td>${billing.apamt1!""}</td>
                                <td>${billing.pmt2!""}</td>
                                <td>${billing.apamt2!""}</td>
                                <td>${billing.notesp!""}</td>
                                <td>${billing.pending?c}</td>
                                <td>${billing.assigned_date!""}</td>
                                <td>${billing.assigned_by!""}</td>
                                <td>${billing.service_category!""}</td>
                                <td class="table-action">
                                    <a href="#"><i class="align-middle fas fa-fw fa-pen"></i></a>
                                    <a href="#"><i class="align-middle fas fa-fw fa-trash"></i></a>
                                </td>
                            </tr>
                        </#list>
                        </tbody>
                        <tfoot>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>
    </div>
</@ui.dashboardUI>
