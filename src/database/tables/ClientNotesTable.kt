package database.tables

import org.jetbrains.exposed.sql.Table

object ClientNotesTable: Table() {
    val client_num = integer("Client#")
    val notedate = datetime("NoteDate")
    val initial = text("Initl")
    val note = text("Note")
    val clientnotekey = integer("ClientNoteKey").primaryKey()
}
