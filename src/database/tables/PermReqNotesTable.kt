package database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime

object PermReqNotesTable: Table() {
    val id = integer("ID")
    override val primaryKey = PrimaryKey(id, name = "ID")
    val emp_num = integer("Emp#")
    val desired_location = text("DesiredLocation")
    val start_date = datetime("StartDate")
    val fulltime = bool("FullTime")
    val desired_days = text("DesiredDays")
    val special_requests = text("SpecialRequests")
    val not_interested = text("NotInterested")
}
