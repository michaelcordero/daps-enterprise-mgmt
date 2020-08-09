package model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import database.tables.PermNotesTable
import model.deserializers.LocalDateDeserializer
import model.serializers.LocalDateSerializer
import java.io.Serializable
import java.sql.ResultSet
import java.sql.Timestamp

data class PermNote
@JsonCreator constructor(
    val id: Int?,
    @JsonProperty(value = "emp_num", required = true)
    val emp_num: Int,
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    val note_date: Timestamp?,
    val initial: String?,
    val comments: String?,
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    val follow_update: Timestamp?
) : Serializable {
    constructor(result_set: ResultSet) : this(
        result_set.getInt(PermNotesTable.ID.name),
        result_set.getInt(PermNotesTable.emp_num.name),
        result_set.getString(PermNotesTable.note_date.name)?.let { Timestamp.valueOf(it) },
        result_set.getString(PermNotesTable.initial.name),
        result_set.getString(PermNotesTable.comments.name),
        result_set.getString(PermNotesTable.follow_update.name)?.let { Timestamp.valueOf(it) }
    )
}
