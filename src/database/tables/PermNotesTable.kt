package database.tables

import database.realtimestamp
import org.jetbrains.exposed.sql.Table

object PermNotesTable: Table() {
    val ID = integer("ID").autoIncrement()
    override val primaryKey = PrimaryKey(ID)
    val emp_num = integer("Emp#")
    val note_date = realtimestamp("NoteDate").nullable()
    val initial = text("Initl").nullable()
    val comments = text("Comments").nullable()
    val follow_update = realtimestamp("FolUpDate").nullable()
}
