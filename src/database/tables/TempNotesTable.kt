package database.tables

import org.jetbrains.exposed.sql.Table

object TempNotesTable: Table() {
    val emp_num = integer("Emp#")
    val note_date = datetime("NoteDate")
    val initial = text("Initl")
    val emp_note = text("EmpNote")
    val temp_note_key = integer("TempNoteKey").primaryKey()
}
