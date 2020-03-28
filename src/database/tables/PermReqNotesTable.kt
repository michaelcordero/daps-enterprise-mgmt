package database.tables

import database.realtimestamp
import org.jetbrains.exposed.sql.Table

object PermReqNotesTable: Table() {
    val id = integer("ID").autoIncrement()
    override val primaryKey = PrimaryKey(id, name = "ID")
    val emp_num = integer("Emp#").nullable()
    val desired_location = text("DesiredLocation").nullable()
    val start_date = realtimestamp("StartDate").nullable()
    val fulltime = bool("FullTime").nullable()
    val desired_days = text("DesiredDays").nullable()
    val special_requests = text("SpecialRequests").nullable()
    val not_interested = text("NotInterested").nullable()
}
