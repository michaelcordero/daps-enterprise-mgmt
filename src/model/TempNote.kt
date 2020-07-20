package model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import database.tables.TempNotesTable
import model.deserializers.LocalDateDeserializer
import model.serializers.LocalDateSerializer
import java.io.Serializable
import java.sql.ResultSet
import java.sql.Timestamp

data class TempNote
@JsonCreator constructor(
    @JsonProperty(value = "emp_num", required = true)
    val emp_num: Int,
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    val note_date: Timestamp?,
    val initial: String?,
    val emp_note: String?,
    val temp_note_key: Int?
) : Serializable {
    constructor(resultSet: ResultSet) : this(
        resultSet.getInt(TempNotesTable.emp_num.name),
        resultSet.getString(TempNotesTable.note_date.name)?.let { Timestamp.valueOf(it) },
        resultSet.getString(TempNotesTable.initial.name),
        resultSet.getString(TempNotesTable.emp_note.name),
        resultSet.getInt(TempNotesTable.temp_note_key.name)
    )
}
