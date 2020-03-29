package database.tables

import database.realtimestamp
import org.jetbrains.exposed.sql.Table

object ClientNotesTable: Table() {
    val clientnotekey = integer("ClientNoteKey").autoIncrement()
    override val primaryKey = PrimaryKey(clientnotekey)
    val client_num = integer("Client#")
    val notedate = realtimestamp("NoteDate").nullable()
    val initial = text("Initl").nullable()
    val note = text("Note").nullable()
}
