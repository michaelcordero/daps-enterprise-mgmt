<#-- @ftlvariable name="presenter" type="presenters.WebTempNotesPresenter" -->
<#import "dashboard-ui.ftl" as ui />
<@ui.dashboardUI title="TempNotes">
    <div class="row">
        <div class="col">
            <div class="card">
                <div class="card-body">
                    <table id="data-tempnotes" class="table table-striped table-bordered table-responsive table-hover"
                           style="width: 100%;">
                        <thead>
                        <tr>
                            <th>Employee#</th>
                            <th>Note Date</th>
                            <th>Initial</th>
                            <th>Emp Note</th>
                            <th>Temp Note Key</th>
                        </tr>
                        </thead>
                        <tfoot>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>
    </div>
<#--    <script src="/static/js/app.js"></script>-->
    <script>
        // Datatables basic
        var table = $('#data-tempnotes').DataTable({
            "ajax": {
                "url": '/web/tempnotes',
                "dataSrc": ''
            },
            "autoWidth": true,
            columns:
                [
                    {data: "emp_num", "width": "10%"},
                    {data: "note_date", "width": "10%"},
                    {data: "initial", "width": "5%"},
                    {data: "emp_note", "width": "65%"},
                    {data: "temp_note_key", "width": "10%"}
                ],
        });
    </script>
</@ui.dashboardUI>
