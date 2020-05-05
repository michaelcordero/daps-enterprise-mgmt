package database.tables

import database.realtimestamp
import org.jetbrains.exposed.sql.Table

object WONotesTable : Table() {
    val ID = integer("ID").autoIncrement()
    override val primaryKey = PrimaryKey(ID)
    val wo_number = integer("WO Number")
    val note_date = realtimestamp("NoteDate").nullable()
    val initial = text("Initl").nullable()
    val comments = text("Comments").nullable()
    val followup_date = realtimestamp("FolUpDate").nullable()
}
