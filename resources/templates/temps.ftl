<#-- @ftlvariable name="presenter" type="presenters.WebTempsPresenter" -->
<#import "dashboard-ui.ftl" as ui />
<@ui.dashboardUI title="Temps">
    <link rel="stylesheet" href="${presenter.theme.css}">
    <main class="content">
        <div class="header">
            <h1 class="header-title">
                Temps
            </h1>
        </div>
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
    </main>
<#--Table Logic-->
    <script>
        $(function () {
            const web_socket = new WebSocket('ws://${presenter.host+':'+presenter.port}/update', ['http'])
            // Realtime Update Event
            web_socket.onmessage = function(event) {
                // Expected server message
                let event_data = JSON.parse(event.data)
                if(event_data['UPDATE'] === 'TEMPS') {
                    table.ajax.reload(null, false)
                } else if (event_data['ALERT'] === 'TEMPS') {
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
                        url: '/web/temps',
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
                        url: '/web/temps',
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
                        url: '/web/temps',
                        "deleteBody": false,
                        "data": function (d) {
                            // removing row key
                            const raw = JSON.stringify(d.data);
                            return raw.substr(raw.indexOf(':') + 1)
                        },
                    },
                },
                table: '#data-temps',
                idSrc: 'emp_num',
                fields: [
                    {label: 'Employee #', name: 'emp_num'},
                    {label: 'Type A', name: 'type_a'},
                    {label: 'Type B', name: 'type_b'},
                    {label: 'Status', name: 'status'},
                    {label: 'First Name', name: 'firstname'},
                    {label: 'Last Name', name: 'lastname'},
                    {label: 'Address 1', name: 'address1'},
                    {label: 'Address 2', name: 'address2'},
                    {label: 'City', name: 'city'},
                    {label: 'State', name: 'state'},
                    {label: 'Zip', name: 'zip'},
                    {label: 'County', name: 'county'},
                    {label: 'Email', name: 'email'},
                    {label: 'Home Phone', name: 'hphone'},
                    {label: 'Work Phone', name: 'wphone'},
                    {label: 'WExt', name: 'wext'},
                    {label: 'Callwork', name: 'callwork'},
                    {label: 'Fax', name: 'fax'},
                    {label: 'Cell Phone', name: 'cellphone'},
                    {label: 'Social Security Number', name: 'social_sec_num'},
                    {label: 'DAPS Start', name: 'daps_start'},
                    {label: 'DAPS Dollar', name: 'daps_dollar'},
                    {label: 'Hourly Rate', name: 'hourly_rate'},
                    {label: 'Chart Complete', name: 'chart_complete'},
                    {label: 'License File', name: 'license_file'},
                    {label: 'License', name: 'license'},
                    {label: 'License Date', name: 'license_date'},
                    {label: 'MALP INS', name: 'malp_ins'},
                    {label: 'MAPL INFO', name: 'mapl_info'},
                    {label: 'Exper Since', name: 'exper_since'},
                    {label: 'Photo ID', name: 'photo_id'},
                    {label: 'Work Auth', name: 'work_auth'},
                    {label: 'Ref Check', name: 'ref_check'},
                    {label: 'Cont Sub Lic', name: 'cont_sub_lic'},
                    {label: 'Exper Type', name: 'exper_type'},
                    {label: 'Emergency Contact', name: 'emergency_contact'},
                    {label: 'Emergency Phone', name: 'emergency_phone'},
                    {label: 'Eext', name: 'eext'},
                    {label: 'Preferences', name: 'preferences'},
                    {label: 'Dislikes', name: 'dislikes'},
                    {label: 'Temp Needs', name: 'temp_needs'},
                    {label: 'Perm Needs', name: 'perm_needs'},
                    {label: 'Computer', name: 'computer'},
                    {label: 'On Call', name: 'oncall'},
                    {label: 'Available Monday', name: 'avail_m'},
                    {label: 'Available Tuesday', name: 'avail_t'},
                    {label: 'Available Wednesday', name: 'avail_w'},
                    {label: 'Available Thursday', name: 'avail_r'},
                    {label: 'Available Friday', name: 'avail_f'},
                    {label: 'Available Saturday', name: 'avail_s'},
                    {label: 'Available Sunday', name: 'avail_u'},
                    {label: 'Available No S', name: 'avail_nos'},
                    {label: 'Pref Loc', name: 'pref_loc'},
                    {label: 'Max Miles', name: 'max_miles'},
                    {label: 'Ref By', name: 'ref_by'},
                    {label: 'Notes', name: 'notes'},
                    {label: 'Account Rep', name: 'account_rep'},
                    {label: 'Account Rep End Date', name: 'account_rep_enddate'},
                    {label: 'Perm Notes', name: 'perm_notes'},
                    {label: 'Set Flag', name: 'set_flag'},
                    {label: 'Yes List', name: 'yes_list'},
                    {label: 'Resident Alien', name: 'resident_alien'},
                    {label: 'Resident Alien Exp', name: 'resident_alien_exp'},
                    {label: 'Filler Two', name: 'filler_two'},
                ]
            });
            editor.disable('emp_num')
            // Datatables basic
            const table = $('#data-temps').DataTable({
                "ajax": {
                    "url": '/web/temps',
                    "dataSrc": ''
                },
                dom: 'lBfrtip',
                columns:
                    [
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
                        {data: 'filler_two'}
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
