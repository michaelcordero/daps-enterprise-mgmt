<#import "dashboard-ui.ftl" as ui />
<@ui.dashboardUI title="Temps">
<#--Table Format-->
    <div class="row">
        <div class="col">
            <div class="card">
                <div class="card-body">
                    <table id="data-temps" class="table table-striped table-bordered table-responsive table-hover"
                           style="width: 100%;">
                        <thead>
                        <tr>
                            <th>Emp#</th>
                            <th>TypeA</th>
                            <th>TypeB</th>
                            <th>Status</th>
                            <th>FName</th>
                            <th>LName</th>
                            <th>Addr1</th>
                            <th>Addr2</th>
                            <th>City</th>
                            <th>State</th>
                            <th>Zip</th>
                            <th>County</th>
                            <th>e-Mail</th>
                            <th>HPhone</th>
                            <th>WPhone</th>
                            <th>WExt</th>
                            <th>CallWrk?</th>
                            <th>Fax</th>
                            <th>CellPhone</th>
                            <th>SS#</th>
                            <th>DAPSstart</th>
                            <th>DAPS$</th>
                            <th>HourlyRate</th>
                            <th>ChartComplete</th>
                            <th>LicenseFile</th>
                            <th>License</th>
                            <th>LicDate</th>
                            <th>MALPins?</th>
                            <th>MAPLinfo</th>
                            <th>ExperSince</th>
                            <th>PhotoId</th>
                            <th>WorkAuth</th>
                            <th>RefCheck</th>
                            <th>ContSubLic</th>
                            <th>ExperType</th>
                            <th>EmrgyContact</th>
                            <th>EmrgyPhone</th>
                            <th>EExt</th>
                            <th>Preferences</th>
                            <th>Dislikes</th>
                            <th>TempNeeds</th>
                            <th>PermNeeds</th>
                            <th>Computer</th>
                            <th>OnCall</th>
                            <th>Avail-M</th>
                            <th>Avail-T</th>
                            <th>Avail-W</th>
                            <th>Avail-R</th>
                            <th>Avail-F</th>
                            <th>Avail-S</th>
                            <th>Avail-U</th>
                            <th>Avail-NoS</th>
                            <th>PrefLoc</th>
                            <th>MaxMiles</th>
                            <th>RefBy</th>
                            <th>Notes</th>
                            <th>AccountRep</th>
                            <th>AccountRepEndDate</th>
                            <th>PermNotes</th>
                            <th>SetFlag</th>
                            <th>YesList</th>
                            <th>Resident Alien</th>
                            <th>Resident Alien Exp</th>
                            <th>Filler-2</th>
                        </tr>
                        </thead>
                        <tfoot>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>
    </div>
<#--Table Logic-->
    <script>
        // var editor = new $.fn.dataTable.Editor({
        //     "ajax": '/web/temps',
        //     "table": '#data-temps',
        // });
        $('#data-temps').DataTable({
            "ajax": {
                "url": '/web/temps',
                "dataSrc": ''
            },
            // dom: 'Bfrtip',
            columns: [
                {data: 'emp_num'},
                {data: 'type_a'},
                {data: 'type_b'},
                {data: 'status'},
                {data: 'firstname'},
                {data: 'lastname'},
                {data: 'address1'},
                {data: 'address2'},
                {data: 'city'},
                {data: 'state'},
                {data: 'zip'},
                {data: 'county'},
                {data: 'email'},
                {data: 'hphone'},
                {data: 'wphone'},
                {data: 'wext'},
                {data: 'callwork'},
                {data: 'fax'},
                {data: 'cellphone'},
                {data: 'social_sec_num'},
                {data: 'daps_start'},
                {data: 'daps_dollar'},
                {data: 'hourly_rate'},
                {data: 'chart_complete'},
                {data: 'license_file'},
                {data: 'license'},
                {data: 'license_date'},
                {data: 'malp_ins'},
                {data: 'mapl_info'},
                {data: 'exper_since'},
                {data: 'photo_id'},
                {data: 'work_auth'},
                {data: 'ref_check'},
                {data: 'cont_sub_lic'},
                {data: 'exper_type'},
                {data: 'emergency_contact'},
                {data: 'emergency_phone'},
                {data: 'eext'},
                {data: 'preferences'},
                {data: 'dislikes'},
                {data: 'temp_needs'},
                {data: 'perm_needs'},
                {data: 'computer'},
                {data: 'oncall'},
                {data: 'avail_m'},
                {data: 'avail_t'},
                {data: 'avail_w'},
                {data: 'avail_r'},
                {data: 'avail_f'},
                {data: 'avail_s'},
                {data: 'avail_u'},
                {data: 'avail_nos'},
                {data: 'pref_loc'},
                {data: 'max_miles'},
                {data: 'ref_by'},
                {data: 'notes'},
                {data: 'account_rep'},
                {data: 'account_rep_enddate'},
                {data: 'perm_notes'},
                {data: 'set_flag'},
                {data: 'yes_list'},
                {data: 'resident_alien'},
                {data: 'resident_alien_exp'},
                {data: 'filler_two'}],
            // select: true,
            // buttons: [
            //     { extend: 'create', editor: editor },
            //     { extend: 'edit',   editor: editor },
            //     { extend: 'remove', editor: editor }
            // ]
        });
    </script>
</@ui.dashboardUI>
