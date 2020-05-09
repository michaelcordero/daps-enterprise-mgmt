package model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import database.tables.TempNotesTable
import java.io.Serializable
import java.sql.ResultSet
import java.sql.Timestamp

data class TempNotes
@JsonCreator constructor(
    @JsonProperty(value = "emp_num", required = true) val emp_num: Int, val note_date: Timestamp?,
    val initial: String?, val emp_note: String?,
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
