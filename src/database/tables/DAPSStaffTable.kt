package database.tables

import org.jetbrains.exposed.sql.Table

object DAPSStaffTable: Table() {
    val initial = text("Initl")
    override val primaryKey = PrimaryKey(initial, name = "Initl")
    val firstname = text("First Name").nullable()
    val lastname = text("Last Name").nullable()
    val department = text("Department").nullable()
}
