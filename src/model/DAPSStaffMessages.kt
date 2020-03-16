package model

import java.time.LocalDateTime


data class DAPSStaffMessages(val memo_date: LocalDateTime?, val entered_by: String?,
                             val intended_for: String?, val message: String?,
                             val staff_messages_key: Int?)
