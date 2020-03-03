package model

import org.joda.time.DateTime

data class TempNotes(val emp_num: Int, val note_date: DateTime?,
                     val initial: String?, val emp_note: String?,
                     val temp_note_key: Int?)
