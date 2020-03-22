package database.tables

import org.jetbrains.exposed.sql.Table

object JobFunctionTable: Table() {
    val dentist = text("Dentist").nullable()
    val hygienist = text("Hygienist").nullable()
    val assistant = text("Assistant").nullable()
    val technician = text("Technician").nullable()
    val ofcmanager = text("OfcManager").nullable()
    val receptionist = text("Receptionist").nullable()
    val sales = text("Sales").nullable()
    val other = text("Other").nullable()
}
