package model

import database.tables.WONotesTable
import java.io.Serializable
import java.sql.ResultSet
import java.time.LocalDateTime

data class WONotes(val id: Int, val wo_number: Int?, val note_date: LocalDateTime?,
                   val initial: String?, val comments: String?, val followup_date: LocalDateTime?) : Serializable {
     constructor(result_set: ResultSet) : this (
         result_set.getInt(WONotesTable.id.name),
         result_set.getInt(WONotesTable.wo_number.name),
         result_set.getString(WONotesTable.note_date.name)?.let {
             LocalDateTime.parse(it.replace(" ", "T"))
         },
         result_set.getString(WONotesTable.initial.name),
         result_set.getString(WONotesTable.comments.name),
         result_set.getString(WONotesTable.followup_date.name)?.let {
             LocalDateTime.parse(it.replace(" ", "T"))
         }
     )
}
