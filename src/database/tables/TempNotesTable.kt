package database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.timestamp

object TempNotesTable: Table() {
    val emp_num = integer("Emp#").nullable()
    val note_date = timestamp("NoteDate").nullable()
    val initial = text("Initl").nullable()
    val emp_note = text("EmpNote").nullable()
    val temp_note_key = integer("TempNoteKey")
    override val primaryKey = PrimaryKey(temp_note_key, name = "TempNoteKey")
}
