package database.tables

import org.jetbrains.exposed.sql.Table

object WONotesTable: Table() {
    val id = integer("ID").primaryKey()
    val wo_number = integer("WO Number")
    val note_date = datetime("NoteDate")
    val initial = text("Initl")
    val comments = text("Comments")
    val followup_date = datetime("FolUpDate")
}
