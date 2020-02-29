package database.tables

import org.jetbrains.exposed.sql.Table

object PermNotesTable: Table() {
    val id = integer("ID").primaryKey()
    val emp_num = integer("Emp#")
    val note_date = datetime("NoteDate")
    val initial = text("Initl")
    val comments = text("Comments")
    val follow_update = datetime("FolUpDate")
}
