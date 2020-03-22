package model

import database.tables.TempNotesTable
import java.io.Serializable
import java.sql.ResultSet
import java.time.LocalDateTime

data class TempNotes(val emp_num: Int, val note_date: LocalDateTime?,
                     val initial: String?, val emp_note: String?,
                     val temp_note_key: Int?) : Serializable {
    constructor(resultSet: ResultSet) : this (
        resultSet.getInt(TempNotesTable.emp_num.name),
        resultSet.getString(TempNotesTable.note_date.name)?.let {
            LocalDateTime.parse(it.replace(" ", "T"))
        },
        resultSet.getString(TempNotesTable.initial.name),
        resultSet.getString(TempNotesTable.emp_note.name),
        resultSet.getInt(TempNotesTable.temp_note_key.name)
    )
}
