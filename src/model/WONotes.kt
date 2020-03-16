package model

import java.time.LocalDateTime

data class WONotes(val id: Int, val wo_number: Int?, val note_date: LocalDateTime?,
                   val initial: String?, val comments: String?, val followup_date: LocalDateTime?)
