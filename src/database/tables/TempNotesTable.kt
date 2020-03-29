package database.tables

import database.realtimestamp
import org.jetbrains.exposed.sql.Table

object TempNotesTable: Table() {
    val emp_num = integer("Emp#").nullable()
    val note_date = realtimestamp("NoteDate").nullable()
    val initial = text("Initl").nullable()
    val emp_note = text("EmpNote").nullable()
    val temp_note_key = integer("TempNoteKey").autoIncrement()
    override val primaryKey = PrimaryKey(temp_note_key)
}
