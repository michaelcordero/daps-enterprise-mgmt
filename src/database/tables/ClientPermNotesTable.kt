package database.tables

import org.jetbrains.exposed.sql.Table

object ClientPermNotesTable: Table() {
    val id = integer("ID").primaryKey()
    val client_num = integer("Client#")
    val wo_num = integer("WO Number")
    val not_interested = text("NotInterested")
    val staffname = text("StaffName")
}
