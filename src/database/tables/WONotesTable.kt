package database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime

object WONotesTable: Table() {
    val id = integer("ID")
    override val primaryKey = PrimaryKey(id, name = "ID")
    val wo_number = integer("WO Number")
    val note_date = datetime("NoteDate")
    val initial = text("Initl")
    val comments = text("Comments")
    val followup_date = datetime("FolUpDate")
}
