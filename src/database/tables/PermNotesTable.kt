package database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime

object PermNotesTable: Table() {
    val id = integer("ID")
    override val primaryKey = PrimaryKey(id, name = "ID")
    val emp_num = integer("Emp#")
    val note_date = datetime("NoteDate")
    val initial = text("Initl")
    val comments = text("Comments")
    val follow_update = datetime("FolUpDate")
}
