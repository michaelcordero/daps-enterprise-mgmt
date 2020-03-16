package model

import java.time.LocalDateTime

data class TempNotes(val emp_num: Int, val note_date: LocalDateTime?,
                     val initial: String?, val emp_note: String?,
                     val temp_note_key: Int?)
