package database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime

object ClientNotesTable: Table() {
    val clientnotekey = integer("ClientNoteKey")
    override val primaryKey = PrimaryKey(clientnotekey, name = "ClientNoteKey")
    val client_num = integer("Client#")
    val notedate = datetime("NoteDate").nullable()
    val initial = text("Initl").nullable()
    val note = text("Note").nullable()
}
