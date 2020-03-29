package database.tables

import org.jetbrains.exposed.sql.Table

object ClientPermNotesTable: Table() {
    val ID = integer("ID").autoIncrement()
    override val primaryKey = PrimaryKey(ID, name = "ID")
    val client_num = integer("Client#").nullable()
    val wo_num = integer("WO Number").nullable()
    val not_interested = text("NotInterested").nullable()
    val staffname = text("StaffName").nullable()
}
