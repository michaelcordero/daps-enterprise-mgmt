
package model

import database.tables.PermNotesTable
import java.io.Serializable
import java.sql.ResultSet
import java.time.LocalDateTime

data class PermNotes(val id: Int?, val emp_num: Int?, val note_date: LocalDateTime?,
                     val initial: String?, val comments: String?,
                     val follow_update: LocalDateTime?) : Serializable {
    constructor(result_set: ResultSet) : this (
        result_set.getInt(PermNotesTable.id.name),
        result_set.getInt(PermNotesTable.emp_num.name),
        result_set.getString(PermNotesTable.note_date.name)?.let {
            LocalDateTime.parse(it.replace(" ", "T"))
        },
        result_set.getString(PermNotesTable.initial.name),
        result_set.getString(PermNotesTable.comments.name),
        result_set.getString(PermNotesTable.follow_update.name)?.let {
            LocalDateTime.parse(it.replace(" ", "T"))
        }
    )
}
