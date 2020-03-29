package database.tables

import database.realtimestamp
import org.jetbrains.exposed.sql.Table


object BillingTable: Table() {
    val counter = integer("Counter").autoIncrement()
    val client_num = integer("Client#")
    val employee_num = integer("Emp#")
    val wdate = realtimestamp("WDate").nullable()
    val hours = double("Hours")
    val start_time = realtimestamp("StartTime").nullable()
    val end_time = realtimestamp("EndTime").nullable()
    val daps_fee = double("DAPSfee").nullable()
    val total_fee = double("TotalFee").nullable()
    val worktype = text("WorkType").nullable()
    val work_order_num = integer("WO number").nullable()
    val open = bool("Open").nullable()
    val pmt1 = text("Pmt1").nullable()
    val apamt1 = double("ApAmt1").nullable()
    val pmt2 = text("Pmt2").nullable()
    val apamt2 = double("ApAmt2").nullable()
    val notesp = text("NotesP").nullable()
    val pending = bool("Pending").nullable()
    val assign_date = realtimestamp("Assign Date").nullable()
    val assigned_by = text("Assigned By").nullable()
    val service_category = text("ServiceCategory").nullable()
}
