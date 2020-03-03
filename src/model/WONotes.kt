package model

import org.joda.time.DateTime

data class WONotes(val id: Int, val wo_number: Int?, val note_date: DateTime?,
                   val initial: String?, val comments: String?, val followup_date: DateTime?)
