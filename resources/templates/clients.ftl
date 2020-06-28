<#-- @ftlvariable name="presenter" type="presenters.WebClientsPresenter" -->
<#import "dashboard-ui.ftl" as ui />
<@ui.dashboardUI title="Clients">
    <div class="row">
        <div class="col">
            <div class="card">
                <div class="card-body">
                    <table id="data-clients" class="table table-striped table-bordered table-responsive table-hover"
                           style="width: 100%;">
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
                            <th>e-Mail</th>
                            <th>OPhone</th>
                            <th>OXtension</th>
                            <th>OFax</th>
                            <th>HPhone</th>
                            <th>CellPhone</th>
                            <th>CarPhone</th>
                            <th>EstDate</th>
                            <th>Specialty</th>
                            <th>OfcHours</th>
                            <th>OManager</th>
                            <th>RateConfirm</th>
                            <th>Agrmt?</th>
                            <th>AgrmtPerm</th>
                            <th>PktSent?</th>
                            <th>RefdBy</th>
                            <th>Preferences</th>
                            <th>Dislikes</th>
                            <th>TempHyg?</th>
                            <th>DAPS$</th>
                            <th>DAPS2$</th>
                            <th>Needs</th>
                            <th>StartDate</th>
                            <th>EndDate</th>
                            <th>Days</th>
                            <th>PermConf?</th>
                            <th>TempConf?</th>
                            <th>MlPlcmnt</th>
                            <th>LofAPlcmnt</th>
                            <th>PatntTime</th>
                            <th>WarnDate1</th>
                            <th>WarnDate2</th>
                            <th>WarnDate3</th>
                            <th>CNotes</th>
                            <th>MultiOffice?</th>
                            <th>PayPeriods</th>
                            <th>YesList</th>
                            <th>Filler</th>
                            <th>Filler2</th>
                            <#--                        <th>Actions</th>-->
                        </tr>
                        </thead>
                        <#--                    <tbody>-->
                        <#--                        <tr>-->
                        <#--                            <td class="table-action">-->
                        <#--                                <a href="#"><i class="align-middle fas fa-fw fa-pen"></i></a>-->
                        <#--                                <a href="#"><i class="align-middle fas fa-fw fa-trash"></i></a>-->
                        <#--                            </td>-->
                        <#--                        </tr>-->
                        <#--                    </tbody>-->
                        <tfoot>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>
    </div>
<#--    <script src="/static/js/app.js"></script>-->
    <script>
        $(function () {
            // Datatables Editor
            var editor = new $.fn.dataTable.Editor({
                ajax: {
                    "url": 'http://localhost:8080/clients',
                    contentType: 'application/json',
                    data: function ( d ) {
                        return JSON.stringify( d.data[1] );
                    },
                    dataType: "json",
                },
                table: '#data-clients',
                idSrc: 'client_num',
                fields: [{
                    name: 'client_num'
                }, {
                    name: 'ofcname'
                }, {
                    name: 'firstname1'
                }, {
                    name: 'lastname1'
                }, {
                    name: 'firstname2'
                }, {
                    name: 'lastname2'
                }, {
                    name: 'address1'
                }, {
                    name: 'address2'
                }, {
                    name: 'city'
                }, {
                    name: 'state'
                }, {
                    name: 'zip'
                }, {
                    name: 'county'
                }, {
                    name: 'email'
                }, {
                    name: 'ophone'
                }, {
                    name: 'oxtension'
                }, {
                    name: 'ofax'
                }, {
                    name: 'hphone'
                }, {
                    name: 'cellphone'
                }, {
                    name: 'carphone'
                }, {
                    name: 'estdate'
                }, {
                    name: 'specialty'
                }, {
                    name: 'ofchrs'
                }, {
                    name: 'ofcmanager,'
                }, {
                    name: 'rate_confirm'
                }, {
                    name: 'agreement'
                }, {
                    name: 'agreement_perm'
                }, {
                    name: 'pktsent'
                }, {
                    name: 'refdby'
                }, {
                    name: 'dislikes'
                }, {
                    name: 'temphyg'
                }, {
                    name: 'daps_dollar'
                }, {
                    name: 'daps_dollar_two'
                }, {
                    name: 'needs'
                }, {
                    name: 'start_date'
                }, {
                    name: 'end_date'
                }, {
                    name: 'days'
                }, {
                    name: 'permconf'
                }, {
                    name: 'tempconf'
                }, {
                    name: 'mlplcmnt'
                }, {
                    name: 'lofaplcmnt'
                }, {
                    name: 'patnttime'
                }, {
                    name: 'warndate1'
                }, {
                    name: 'warndate2'
                }, {
                    name: 'warndate3'
                }, {
                    name: 'cnotes'
                }, {
                    name: 'multioffice'
                }, {
                    name: 'payperiods'
                }, {
                    name: 'yeslist'
                }, {
                    name: 'filler'
                }, {
                    name: 'filler2'
                }]
            });
            // Submit if all changed
            $('#data-clients').on('click', 'tbody td:not(:first-child)', function (e) {
                editor.inline(this, {
                    submit: 'all'
                });
            });
            // Data Table
            $('#data-clients').DataTable({
                "ajax": {
                    "url": 'http://localhost:8080/clients',
                    "dataSrc": '',
                },
                // serverSide: true,
                dom: 'Bfrtip',
                columns:
                    [
                        {data: 'client_num'},
                        {data: 'ofcname'},
                        {data: 'firstname1'},
                        {data: 'lastname1'},
                        {data: 'firstname2'},
                        {data: 'lastname2'},
                        {data: 'address1'},
                        {data: 'address2'},
                        {data: 'city'},
                        {data: 'state'},
                        {data: 'zip'},
                        {data: 'county'},
                        {data: 'email'},
                        {data: 'ophone'},
                        {data: 'oxtension'},
                        {data: 'ofax'},
                        {data: 'hphone'},
                        {data: 'cellphone'},
                        {data: 'carphone'},
                        {data: 'estdate'},
                        {data: 'specialty'},
                        {data: 'ofchrs'},
                        {data: 'ofcmanager'},
                        {data: 'rate_confirm'},
                        {data: 'agreement'},
                        {data: 'agreement_perm'},
                        {data: 'pktsent'},
                        {data: 'refdby'},
                        {data: 'preferences'},
                        {data: 'dislikes'},
                        {data: 'temphyg'},
                        {data: 'daps_dollar'},
                        {data: 'daps_dollar_two'},
                        {data: 'needs'},
                        {data: 'start_date'},
                        {data: 'end_date'},
                        {data: 'days'},
                        {data: 'permconf'},
                        {data: 'tempconf'},
                        {data: 'mlplcmnt'},
                        {data: 'lofaplcmnt'},
                        {data: 'patnttime'},
                        {data: 'warndate1'},
                        {data: 'warndate2'},
                        {data: 'warndate3'},
                        {data: 'cnotes'},
                        {data: 'multioffice'},
                        {data: 'payperiods'},
                        {data: 'yeslist'},
                        {data: 'filler'},
                        {data: 'filler2'}],
                select: true,
                buttons: [
                    'pageLength',
                    {extend: 'create', editor: editor},
                    {extend: 'edit', editor: editor},
                    {extend: 'remove', editor: editor}
                ]
            });
        });
    </script>
</@ui.dashboardUI>
