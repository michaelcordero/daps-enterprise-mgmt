package database.tables

import org.jetbrains.exposed.sql.Table

object WorkOrderTable: Table() {
    val wo_number = integer("WO Number").primaryKey()
    val client_num = integer("Client#")
    val emp_num = integer("Emp#")
    val temp_perm = text("TempPerm")
    val filled_date = datetime("Filled Date")
    val filled_rate = decimal("Filled Rate", 15,15)
    val start_date = datetime("StartDate")
    val start_time = datetime("StartTime")
    val end_time = datetime("EndTime")
    val services_category = text("ServiceCategory")
    val job_description = text("JobDescription")
    val skills_required = text("SkillsRequired")
    val work_hours = text("WorkHours")
    val will_train = bool("WillTrain")
    val confidential = bool("Confidential")
    val contact_name = text("ContactName")
    val fees_discussed = bool("FeesDiscussed")
    val note = text("Note")
    val entered_by = text("EntrdBy")
    val entered_date = datetime("EntrdDate")
    val post = bool("Post")
    val active = bool("Active")
    val left_message = datetime("LeftMessage")
    val confirmed = bool("Confirmed")
}
