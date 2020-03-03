package model

import org.joda.time.DateTime

data class InterviewGuide(val id: Int?, val client_num: Int?, val client_contact: String?,
                          val employee_num: Int?, val employee_name: String?,
                          val referral_date: DateTime, val referral_notes: String?,
                          val interview_complete: Boolean?, val interview_notes: String?,
                          val wo_number: Int?, val emp_notes_id: Int?, val client_notes_id: Int?)
