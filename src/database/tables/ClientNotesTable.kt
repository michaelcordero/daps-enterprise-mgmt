package database.tables

import database.realtimestamp
import org.jetbrains.exposed.sql.Table

object ClientNotesTable: Table() {
    val clientnotekey = integer("ClientNoteKey")
    override val primaryKey = PrimaryKey(clientnotekey, name = "ClientNoteKey")
    val client_num = integer("Client#")
    val notedate = realtimestamp("NoteDate").nullable()
    val initial = text("Initl").nullable()
    val note = text("Note").nullable()
}
