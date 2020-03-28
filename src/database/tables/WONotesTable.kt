package database.tables

import database.realtimestamp
import org.jetbrains.exposed.sql.Table

object WONotesTable: Table() {
    val id = integer("ID").autoIncrement()
    override val primaryKey = PrimaryKey(id, name = "ID")
    val wo_number = integer("WO Number").nullable()
    val note_date = realtimestamp("NoteDate").nullable()
    val initial = text("Initl").nullable()
    val comments = text("Comments").nullable()
    val followup_date = realtimestamp("FolUpDate").nullable()
}
