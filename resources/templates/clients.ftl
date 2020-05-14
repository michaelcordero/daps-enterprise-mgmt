<#-- @ftlvariable name="clients" type="java.util.List<model.ClientFile>" -->
<#-- @ftlvariable name="presenter" type="presenters.WebClientsPresenter" -->
<#import "dashboard-ui.ftl" as ui />
<@ui.dashboardUI title="Clients">
    <div class="row">
    <div class="col">
        <div class="card">
            <div class="card-body">
                <table id="datatables-basic" class="table table-striped table-bordered table-responsive " style="width: 100%;">
                    <thead>
                    <tr>
                        <th>Client#</th>
                        <th>OfficeName</th>
                        <th>FirstName1</th>
                        <th>LastName1</th>
                        <th>FirstName2</th>
                        <th>LastName2</th>
                        <th>Address1</th>
                        <th>Address2</th>
                        <th>City</th>
                        <th>State</th>
                        <th>Zip</th>
                        <th>County</th>
                        <th>Email</th>
                        <th>OPhone</th>
                        <th>OExtension</th>
                        <th>OFax</th>
                        <th>HPhone</th>
                        <th>CellPhone</th>
                        <th>CarPhone</th>
                        <th>EstDate</th>
                        <th>Specialty</th>
                        <th>OFCHours</th>
                        <th>OManager</th>
                        <th>RateConfirm</th>
                        <th>Agreement</th>
                        <th>Perm</th>
                        <th>PacketSent</th>
                        <th>RefdBy</th>
                        <th>Preferences</th>
                        <th>Dislikes</th>
                        <th>TempHyg</th>
                        <th>DAPSDollar</th>
                        <th>DAPSDollarTwo</th>
                        <th>Needs</th>
                        <th>StartDate</th>
                        <th>EndDate</th>
                        <th>Days</th>
                        <th>PermConf</th>
                        <th>TempConf</th>
                        <th>Mlplcmnt</th>
                        <th>Lofaplcmnt</th>
                        <th>Patnttime</th>
                        <th>WarnDate1</th>
                        <th>WarnDate2</th>
                        <th>WarnDate3</th>
                        <th>CNotes</th>
                        <th>MultiOffice</th>
                        <th>PayPeriods</th>
                        <th>YesList</th>
                        <th>Filler</th>
                        <th>Filler2</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list clients as client>
                        <tr>
                            <td>${client.client_num!""}</td>
                            <td>${client.ofcname!""}</td>
                            <td>${client.firstname1!""}</td>
                            <td>${client.lastname1!""}</td>
                            <td>${client.firstname2!""}</td>
                            <td>${client.lastname2!""}</td>
                            <td>${client.address1!""}</td>
                            <td>${client.address2!""}</td>
                            <td>${client.city!""}</td>
                            <td>${client.state!""}</td>
                            <td>${client.zip!""}</td>
                            <td>${client.county!""}</td>
                            <td>${client.email!""}</td>
                            <td>${client.ophone!""}</td>
                            <td>${client.oxtension!""}</td>
                            <td>${client.ofax!""}</td>
                            <td>${client.hphone!""}</td>
                            <td>${client.cellphone!""}</td>
                            <td>${client.carphone!""}</td>
                            <td>${client.estdate!""}</td>
                            <td>${client.specialty!""}</td>
                            <td>${client.ofchrs!""}</td>
                            <td>${client.ofcmanager!""}</td>
                            <td>${client.rate_confirm?c}</td>
                            <td>${client.agreement!""}</td>
                            <td>${client.agreement_perm!""}</td>
                            <td>${client.pktsent!""}</td>
                            <td>${client.refdby!""}</td>
                            <td>${client.preferences!""}</td>
                            <td>${client.dislikes!""}</td>
                            <td>${client.temphyg?c}</td>
                            <td>${client.daps_dollar!""}</td>
                            <td>${client.daps_dollar_two!""}</td>
                            <td>${client.needs!""}</td>
                            <td>${client.start_date!""}</td>
                            <td>${client.end_date!""}</td>
                            <td>${client.days!""}</td>
                            <td>${client.permconf?c}</td>
                            <td>${client.tempconf?c}</td>
                            <td>${client.mlplcmnt?c}</td>
                            <td>${client.lofaplcmnt?c}</td>
                            <td>${client.patnttime!""}</td>
                            <td>${client.warndate1!""}</td>
                            <td>${client.warndate2!""}</td>
                            <td>${client.warndate3!""}</td>
                            <td>${client.cnotes!""}</td>
                            <td>${client.multioffice!""}</td>
                            <td>${client.payperiods!""}</td>
                            <td>${client.yeslist?c}</td>
                            <td>${client.filler!""}</td>
                            <td>${client.filler2!""}</td>
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
