package model

import org.joda.time.DateTime

data class PermNotes(val id: Int?, val emp_num: Int?, val note_date: DateTime?,
                     val initial: String?, val comments: String?,
                     val follow_update: DateTime?)
