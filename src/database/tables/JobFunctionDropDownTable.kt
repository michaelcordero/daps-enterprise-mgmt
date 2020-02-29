package database.tables

import org.jetbrains.exposed.sql.Table

object JobFunctionDropDownTable: Table() {
    val dentist = text("Dentist")
    val hygienist = text("Hygienist")
    val assistant = text("Assistant")
    val technician = text("Technician")
    val ofcmanager = text("OfcManager")
    val receptionist = text("Receptionist")
    val sales = text("Sales")
    val other = text("Other")
}
