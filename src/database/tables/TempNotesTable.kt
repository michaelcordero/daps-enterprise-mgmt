package database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime

object TempNotesTable: Table() {
    val emp_num = integer("Emp#")
    val note_date = datetime("NoteDate")
    val initial = text("Initl")
    val emp_note = text("EmpNote")
    val temp_note_key = integer("TempNoteKey")
    override val primaryKey = PrimaryKey(temp_note_key, name = "TempNoteKey")
}
