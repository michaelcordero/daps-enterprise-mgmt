package model

import org.joda.time.DateTime

data class PermReqNotes(val id: Int, val emp_num: Int?, val desired_location: String?,
                        val start_date: DateTime?, val fulltime: Boolean?,
                        val desired_days: String?, val special_requests: String?,
                        val not_interested: String? )

