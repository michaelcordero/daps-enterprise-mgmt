package database.tables

import org.jetbrains.exposed.sql.Table


object BillingTable: Table() {
    val counter = integer("Counter")
    val client_num = integer("Client#")
    val employee_num = integer("Emp#")
    val wdate = datetime("WDate")
    val hours = decimal("Hours", 15,15)
    val start_time = datetime("StartTime")
    val end_time = datetime("EndTime")
    val daps_fee = decimal("DAPSfee",15,15)
    val total_fee = decimal("TotalFee",15,15 )
    val worktype = text("WorkType")
    val work_order_num = integer("WO number")
    val open = bool("Open")
    val pmt1 = text("Pmt1")
    val apamt1 = decimal("ApAmt1",15,15)
    val pmt2 = text("Pmt2")
    val apamt2 = decimal("ApAmt2",15,15)
    val notesp = text("NotesP")
    val pending = bool("Pending")
    val assign_date = datetime("Assign Date")
    val assigned_by = text("Assigned By")
    val service_category = text("Service Category")
}
