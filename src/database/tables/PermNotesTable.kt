package database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.timestamp

object PermNotesTable: Table() {
    val id = integer("ID")
    override val primaryKey = PrimaryKey(id, name = "ID")
    val emp_num = integer("Emp#")
    val note_date = timestamp("NoteDate").nullable()
    val initial = text("Initl").nullable()
    val comments = text("Comments").nullable()
    val follow_update = timestamp("FolUpDate").nullable()
}
