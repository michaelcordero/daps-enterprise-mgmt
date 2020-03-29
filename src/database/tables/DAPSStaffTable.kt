package database.tables

import org.jetbrains.exposed.sql.Table

object DAPSStaffTable: Table() {
    val initial = varchar("Initl", 2)
    override val primaryKey = PrimaryKey(initial)
    val firstname = text("First Name").nullable()
    val lastname = text("Last Name").nullable()
    val department = text("Department").nullable()
}
