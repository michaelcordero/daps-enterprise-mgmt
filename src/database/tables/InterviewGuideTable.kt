package database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime

object InterviewGuideTable: Table() {
    val id = integer("ID")
    override val primaryKey = PrimaryKey(id, name = "ID")
    val client_num = integer("Client#")
    val client_contact = text("ClientContact")
    val employee_num = integer("Emp#")
    val employee_name = text("Employee Name")
    val referral_date = datetime("ReferralDate")
    val referral_notes = text("ReferralNotes")
    val interview_complete = bool("InterviewComplete")
    val interview_notes = text("InterviewNotes")
    val wo_number = integer("WO Number")
    val emp_notes_id = integer("EmpNotesID")
    val client_notes_id = integer("ClientNotesID")
}
