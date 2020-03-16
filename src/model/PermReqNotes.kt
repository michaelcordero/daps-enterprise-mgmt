package model

import java.time.LocalDateTime

data class PermReqNotes(val id: Int, val emp_num: Int?, val desired_location: String?,
                        val start_date: LocalDateTime?, val fulltime: Boolean?,
                        val desired_days: String?, val special_requests: String?,
                        val not_interested: String? )

