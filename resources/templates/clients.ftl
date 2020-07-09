<#-- @ftlvariable name="presenter" type="presenters.WebClientsPresenter" -->
<#import "dashboard-ui.ftl" as ui />
<@ui.dashboardUI title="Clients">
    <link rel="stylesheet" href="${presenter.theme.css}">
        <main class="content">
                <div class="header">
                    <h1 class="header-title">
                        Clients
                    </h1>
                </div>
                <div class="row">
                    <div class="col">
                        <div class="card">
                            <div class="card-body">
                                <table id="data-clients"
                                       class="table table-striped table-bordered table-responsive table-hover"
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
            const web_socket = new WebSocket('ws://localhost:8080/update', ['http'])
            // Realtime Update Event
            web_socket.onmessage = function(event) {
                // Additional server logic prevents this from locally updating which would be redundant.
                if(event.data === 'clients') {
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
                        url: 'http://localhost:8080/web/clients',
                        "data": function (d) {
                            // removing row key
                            const raw = JSON.stringify(d.data);
                            return raw.substr(raw.indexOf(':') + 1)
                        },
                        success: function () {
                            web_socket.send("clients")
                        }
                        // error: function (xhr, error, thrown) {
                        //     alert("Save Failed!" + error)
                        // }
                    },
                    edit: {
                        // Defaults
                        contentType: 'application/json',
                        dataType: "json",
                        type: 'PUT',
                        url: 'http://localhost:8080/web/clients',
                        "data": function (d) {
                            // removing row key
                            const raw = JSON.stringify(d.data);
                            return raw.substr(raw.indexOf(':') + 1)
                        },
                        success: function () {
                            web_socket.send("clients")
                        }
                    },
                    remove: {
                        // Defaults
                        contentType: 'application/json',
                        dataType: "json",
                        type: 'DELETE',
                        url: 'http://localhost:8080/web/clients',
                        "deleteBody": false,
                        "data": function (d) {
                            // removing row key
                            const raw = JSON.stringify(d.data);
                            return raw.substr(raw.indexOf(':') + 1)
                        },
                        success: function () {
                            web_socket.send("clients")
                        },
                    },
                },
                table: '#data-clients',
                idSrc: 'client_num',
                fields: [{
                    label: 'Client #', name: 'client_num',
                }, {
                    label: 'Office Name', name: 'ofcname'
                }, {
                    label: 'First Name 1', name: 'firstname1'
                }, {
                    label: 'Last Name 1', name: 'lastname1'
                }, {
                    label: 'First Name 2', name: 'firstname2'
                }, {
                    label: 'Last Name 2', name: 'lastname2'
                }, {
                    label: 'Address 1', name: 'address1'
                }, {
                    label: 'Address 2', name: 'address2'
                }, {
                    label: 'City', name: 'city'
                }, {
                    label: 'State', name: 'state'
                }, {
                    label: 'Zip', name: 'zip'
                }, {
                    label: 'County', name: 'county'
                }, {
                    label: 'Email', name: 'email'
                }, {
                    label: 'Office Phone', name: 'ophone'
                }, {
                    label: 'Office Extension', name: 'oxtension'
                }, {
                    label: 'Office Fax', name: 'ofax'
                }, {
                    label: 'Home Phone', name: 'hphone'
                }, {
                    label: 'Cell Phone', name: 'cellphone'
                }, {
                    label: 'Car Phone', name: 'carphone'
                }, {
                    label: 'Est Date', name: 'estdate'
                }, {
                    label: 'Speciality', name: 'specialty'
                }, {
                    label: 'Office Hours', name: 'ofchrs'
                }, {
                    label: 'Office Manager', name: 'ofcmanager'
                }, {
                    label: 'Rate Confirm', name: 'rate_confirm'
                }, {
                    label: 'Agreement', name: 'agreement'
                }, {
                    label: 'Agreement Perm', name: 'agreement_perm'
                }, {
                    label: 'Packet Sent', name: 'pktsent'
                }, {
                    label: 'RefdBy', name: 'refdby'
                }, {
                    label: 'Preferences', name: 'preferences'
                }
                    , {
                        label: 'Dislikes', name: 'dislikes'
                    }, {
                        label: 'Temp Hyg', name: 'temphyg'
                    }, {
                        label: 'DAPS $', name: 'daps_dollar'
                    }, {
                        label: 'DAPS $ 2', name: 'daps_dollar_two'
                    }, {
                        label: 'Needs', name: 'needs'
                    }, {
                        label: 'Start Date', name: 'start_date'
                    }, {
                        label: 'End Date', name: 'end_date'
                    }, {
                        label: 'Days', name: 'days'
                    }, {
                        label: 'Perm Conf', name: 'permconf'
                    }, {
                        label: 'Temp Conf', name: 'tempconf'
                    }, {
                        label: 'MlPlCmnt', name: 'mlplcmnt'
                    }, {
                        label: 'LofaPlcmnt', name: 'lofaplcmnt'
                    }, {
                        label: 'Patnt Time', name: 'patnttime'
                    }, {
                        label: 'Warn Date 1', name: 'warndate1'
                    }, {
                        label: 'Warn Date 2', name: 'warndate2'
                    }, {
                        label: 'Warn Date 3', name: 'warndate3'
                    }, {
                        label: 'C Notes', name: 'cnotes'
                    }, {
                        label: 'Multi Office', name: 'multioffice'
                    }, {
                        label: 'Pay Periods', name: 'payperiods'
                    }, {
                        label: 'Yes List', name: 'yeslist'
                    }, {
                        label: 'Filler', name: 'filler'
                    }, {
                        label: 'Filler 2', name: 'filler2'
                    }]
            });
            editor.disable('client_num')
            // Data Table
            const table = $('#data-clients').DataTable({
                "ajax": {
                    "type": 'GET',
                    "url": 'http://localhost:8080/web/clients',
                    "dataSrc": '',
                },
                dom: 'lBfrtip',
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
                    // 'pageLength',
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
