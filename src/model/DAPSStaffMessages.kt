package model

import org.joda.time.DateTime

data class DAPSStaffMessages(val memo_date: DateTime?, val entered_by: String?,
                             val intended_for: String?, val message: String?,
                             val staff_messages_key: Int?)
