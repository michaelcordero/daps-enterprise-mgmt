package database.tables

import database.realtimestamp
import org.jetbrains.exposed.sql.Table

object InterviewGuideTable: Table() {
    val id = integer("ID")
    override val primaryKey = PrimaryKey(id, name = "ID")
    val client_num = integer("Client#")
    val client_contact = text("ClientContact").nullable()
    val employee_num = integer("Emp#")
    val employee_name = text("Employee Name").nullable()
    val referral_date = realtimestamp("ReferralDate").nullable()
    val referral_notes = text("ReferralNotes").nullable()
    val interview_complete = bool("InterviewComplete").nullable()
    val interview_notes = text("InterviewNotes").nullable()
    val wo_number = integer("WO Number").nullable()
    val emp_notes_id = integer("EmpNotesID").nullable()
    val client_notes_id = integer("ClientNotesID").nullable()
}
