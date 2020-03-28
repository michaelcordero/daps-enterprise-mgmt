package database.tables

import database.realtimestamp
import org.jetbrains.exposed.sql.Table

object WorkOrderTable: Table() {
    val wo_number = integer("WO Number").autoIncrement()
    override val primaryKey = PrimaryKey(wo_number, name = "WO Number")
    val client_num = integer("Client#").nullable()
    val emp_num = integer("Emp#").nullable()
    val temp_perm = text("TempPerm").nullable()
    val filled_date = realtimestamp("Filled Date").nullable()
    val filled_rate = double("Filled Rate").nullable()
    val start_date = realtimestamp("StartDate").nullable()
    val start_time = realtimestamp("StartTime").nullable()
    val end_time = realtimestamp("EndTime").nullable()
    val services_category = text("ServiceCategory").nullable()
    val job_description = text("JobDescription").nullable()
    val skills_required = text("SkillsRequired").nullable()
    val work_hours = text("WorkHours").nullable()
    val will_train = bool("WillTrain").nullable()
    val confidential = bool("Confidential").nullable()
    val contact_name = text("ContactName").nullable()
    val fees_discussed = bool("FeesDiscussed").nullable()
    val note = text("Note").nullable()
    val entered_by = text("EntrdBy").nullable()
    val entered_date = realtimestamp("EntrdDate").nullable()
    val post = bool("Post").nullable()
    val active = bool("Active").nullable()
    val left_message = realtimestamp("LeftMessage").nullable()
    val confirmed = bool("Confirmed").nullable()
}
