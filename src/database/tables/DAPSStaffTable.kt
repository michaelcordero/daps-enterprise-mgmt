package database.tables

import org.jetbrains.exposed.sql.Table

object DAPSStaffTable: Table() {
    val initial = text("Initl").primaryKey()
    val firstname = text("First Name")
    val lastname = text("Last Name")
    val department = text("Department")
}
