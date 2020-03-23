package database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.timestamp

object WONotesTable: Table() {
    val id = integer("ID")
    override val primaryKey = PrimaryKey(id, name = "ID")
    val wo_number = integer("WO Number").nullable()
    val note_date = timestamp("NoteDate").nullable()
    val initial = text("Initl").nullable()
    val comments = text("Comments").nullable()
    val followup_date = timestamp("FolUpDate").nullable()
}
