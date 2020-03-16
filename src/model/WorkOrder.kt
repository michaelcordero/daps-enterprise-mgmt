package model

import java.time.LocalDateTime

data class WorkOrder(val wo_number: Int, val client_num: Int, val emp_num: Int,
                     val temp_perm: String?, val filled_date: LocalDateTime?,
                     val filled_rate: Double?, val start_date: LocalDateTime?,
                     val start_time: LocalDateTime?, val end_time: LocalDateTime?,
                     val services_category: String?, val job_description: String?,
                     val skills_required: String?, val work_hours: String?,
                     val will_train: Boolean?, val confidential: Boolean?,
                     val contact_name: String?, val fees_discussed: Boolean?,
                     val note: String?, val entered_by: String?, val entered_date: LocalDateTime?,
                     val post: Boolean?, val active: Boolean?, val left_message: LocalDateTime?,
                     val confirmed: Boolean?)
