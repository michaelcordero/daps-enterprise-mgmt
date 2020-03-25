package model

import database.tables.ClientNotesTable
import java.io.Serializable
import java.sql.ResultSet
import java.sql.Timestamp

data class ClientNotes(val client_num: Int, val notedate: Timestamp?,
                       val initial: String?, val note: String?,
                       val client_note_key: Int) : Serializable {
    constructor(resultSet: ResultSet) : this(
        resultSet.getInt(ClientNotesTable.client_num.name),
        resultSet.getString(ClientNotesTable.notedate.name)?.let { Timestamp.valueOf(it) },
        resultSet.getString(ClientNotesTable.initial.name),
        resultSet.getString(ClientNotesTable.note.name),
        resultSet.getInt(ClientNotesTable.clientnotekey.name)
    )
}
