package model

import java.time.LocalDateTime

data class PermNotes(val id: Int?, val emp_num: Int?, val note_date: LocalDateTime?,
                     val initial: String?, val comments: String?,
                     val follow_update: LocalDateTime?)
