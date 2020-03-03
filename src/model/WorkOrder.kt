package model

import org.joda.time.DateTime

data class WorkOrder(val wo_number: Int, val client_num: Int, val emp_num: Int,
                     val temp_perm: String?, val filled_date: DateTime?,
                     val filled_rate: Double?, val start_date: DateTime?,
                     val start_time: DateTime?, val end_time: DateTime?,
                     val services_category: String?, val job_description: String?,
                     val skills_required: String?, val work_hours: String?,
                     val will_train: Boolean?, val confidential: Boolean?,
                     val contact_name: String?, val fees_discussed: Boolean?,
                     val note: String?, val entered_by: String?, val entered_date: DateTime?,
                     val post: Boolean?, val active: Boolean?, val left_message: DateTime?,
                     val confirmed: Boolean?)
