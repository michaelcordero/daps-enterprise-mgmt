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
                            <th>Temp Note Key</th>
                            <th>Employee#</th>
                            <th>Note Date</th>
                            <th>Initial</th>
                            <th>Emp Note</th>
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
                    {data: "temp_note_key"},
                    {data: "emp_num"},
                    {data: "note_date"},
                    {data: "initial"},
                    {data: "emp_note", "width": "75%"}
                ],
        });
        // $('#container').css('display', 'block');
        // table.columns.adjust().draw();
    </script>
</@ui.dashboardUI>