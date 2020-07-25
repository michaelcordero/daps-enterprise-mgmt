package model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import database.tables.ClientNotesTable
import model.deserializers.LocalDateDeserializer
import model.serializers.LocalDateSerializer
import java.io.Serializable
import java.sql.ResultSet
import java.sql.Timestamp

data class ClientNote
@JsonCreator
constructor(
    @JsonProperty(value = "client_num", required = true)
    val client_num: Int,
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    val notedate: Timestamp?,
    val initial: String?,
    val note: String?,
    val client_note_key: Int?
) : Serializable {
    constructor(resultSet: ResultSet) : this(
        resultSet.getInt(ClientNotesTable.client_num.name),
        resultSet.getString(ClientNotesTable.notedate.name)?.let { Timestamp.valueOf(it) },
        resultSet.getString(ClientNotesTable.initial.name),
        resultSet.getString(ClientNotesTable.note.name),
        resultSet.getInt(ClientNotesTable.clientnotekey.name)
    )
}
