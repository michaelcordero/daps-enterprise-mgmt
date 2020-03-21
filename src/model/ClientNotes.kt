package model

import database.tables.ClientNotesTable
import java.io.Serializable
import java.sql.ResultSet
import java.time.LocalDateTime

data class ClientNotes(val client_num: Int, val notedate: LocalDateTime?,
                       val initial: String?, val note: String?,
                       val client_note_key: Int?) : Serializable {
    constructor(resultSet: ResultSet) : this(
        resultSet.getInt(ClientNotesTable.client_num.name),
        resultSet.getString(ClientNotesTable.notedate.name)?.let {
            LocalDateTime.parse(it.replace(" ", "T")) },
        resultSet.getString(ClientNotesTable.initial.name),
        resultSet.getString(ClientNotesTable.note.name),
        resultSet.getInt(ClientNotesTable.clientnotekey.name)
    )
}
