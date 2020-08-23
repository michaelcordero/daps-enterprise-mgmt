package model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import database.tables.WONotesTable
import model.deserializers.LocalDateDeserializer
import model.serializers.LocalDateSerializer
import java.io.Serializable
import java.sql.ResultSet
import java.sql.Timestamp

data class WONote
    @JsonCreator constructor(
        @JsonProperty(value = "id", required = true)
        val id: Int,
        @JsonProperty(value = "wo_number", required = true)
        val wo_number: Int,
        @JsonSerialize(using = LocalDateSerializer::class)
        @JsonDeserialize(using = LocalDateDeserializer::class)
        val note_date: Timestamp?,
        val initial: String?,
        val comments: String?,
        @JsonSerialize(using = LocalDateSerializer::class)
        @JsonDeserialize(using = LocalDateDeserializer::class)
        val followup_date: Timestamp?) : Serializable {
     constructor(result_set: ResultSet) : this (
         result_set.getInt(WONotesTable.ID.name),
         result_set.getInt(WONotesTable.wo_number.name),
         result_set.getString(WONotesTable.note_date.name)?.let { Timestamp.valueOf(it) },
         result_set.getString(WONotesTable.initial.name),
         result_set.getString(WONotesTable.comments.name),
         result_set.getString(WONotesTable.followup_date.name)?.let {Timestamp.valueOf(it) }
     )
}
