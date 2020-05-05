package model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import database.tables.PermNotesTable
import java.io.Serializable
import java.sql.ResultSet
import java.sql.Timestamp

data class PermNotes
@JsonCreator constructor(
    val id: Int?,
    @JsonProperty(value = "emp_num", required = true) val emp_num: Int,
    val note_date: Timestamp?,
    val initial: String?, val comments: String?,
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
