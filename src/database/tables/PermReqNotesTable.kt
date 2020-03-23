package database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.timestamp

object PermReqNotesTable: Table() {
    val id = integer("ID")
    override val primaryKey = PrimaryKey(id, name = "ID")
    val emp_num = integer("Emp#").nullable()
    val desired_location = text("DesiredLocation").nullable()
    val start_date = timestamp("StartDate").nullable()
    val fulltime = bool("FullTime").nullable()
    val desired_days = text("DesiredDays").nullable()
    val special_requests = text("SpecialRequests").nullable()
    val not_interested = text("NotInterested").nullable()
}
