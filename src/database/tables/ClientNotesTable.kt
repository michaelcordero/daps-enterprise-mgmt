package database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.timestamp

object ClientNotesTable: Table() {
    val clientnotekey = integer("ClientNoteKey")
    override val primaryKey = PrimaryKey(clientnotekey, name = "ClientNoteKey")
    val client_num = integer("Client#")
    val notedate = timestamp("NoteDate").nullable()
    val initial = text("Initl").nullable()
    val note = text("Note").nullable()
}
