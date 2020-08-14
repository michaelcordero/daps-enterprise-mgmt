<#-- @ftlvariable name="presenter" type="presenters.WebPaymentsPresenter" -->
<#import "dashboard-ui.ftl" as ui />
<@ui.dashboardUI title="Payments">
    <link rel="stylesheet" href="${presenter.theme.css}">
    <main class="content">
        <div class="header">
            <h1 class="header-title">
                Payments
            </h1>
        </div>
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-body"> <!-- style="width: fit-content" -->
                        <table id="data-payments"
                               class="table table-striped table-bordered table-responsive table-hover"
                               style="width: 100%;">
                            <thead>
                            <tr>
                                <th>Client #</th>
                                <th>Payment Type</th>
                                <th>Reference #</th>
                                <th>Payment Date</th>
                                <th>Amount</th>
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
                if (event.data === 'payments') {
                    table.ajax.reload(null, false)
                } else if (event.data === 'alert:payments') {
                    alert('Data save change failed. Reverting data.')
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
                        url: '/web/payments',
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
                        url: '/web/payments',
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
                        url: '/web/payments',
                        "deleteBody": false,
                        "data": function (d) {
                            // removing row key
                            const raw = JSON.stringify(d.data);
                            return raw.substr(raw.indexOf(':') + 1)
                        },
                    },
                },
                table: '#data-payments',
                idSrc: 'ref_num',
                fields: [
                    {label: 'Client #', name: 'client_num'},
                    {label: 'Payment Type', name: 'pmt_type'},
                    {label: 'Reference #', name: 'ref_num'},
                    {label: 'Payment Date', name: 'pmt_date'},
                    {label: 'Amount', name: 'amount'},
                ]
            });
            // Primary Key
            // editor.disable('ref_num')
            // Datatables basic
            const table = $('#data-payments').DataTable({
                "ajax": {
                    "url": '/web/payments',
                    "dataSrc": ''
                },
                dom: 'lBfrtip',
                "autoWidth": true,
                columns:
                    [
                        {data: "client_num"},
                        {data: "pmt_type"},
                        {data: "ref_num"},
                        {data: "pmt_date"},
                        {data: "amount"}
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
