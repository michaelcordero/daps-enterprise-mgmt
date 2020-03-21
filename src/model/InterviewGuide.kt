package model

import database.tables.InterviewGuideTable
import java.io.Serializable
import java.sql.ResultSet
import java.time.LocalDateTime

data class InterviewGuide(val id: Int?, val client_num: Int?, val client_contact: String?,
                          val employee_num: Int?, val employee_name: String?,
                          val referral_date: LocalDateTime?, val referral_notes: String?,
                          val interview_complete: Boolean?, val interview_notes: String?,
                          val wo_number: Int?, val emp_notes_id: Int?, val client_notes_id: Int?) : Serializable {
    constructor(resultSet: ResultSet) : this (
        resultSet.getInt(InterviewGuideTable.id.name),
        resultSet.getInt(InterviewGuideTable.client_num.name),
        resultSet.getString(InterviewGuideTable.client_contact.name),
        resultSet.getInt(InterviewGuideTable.employee_num.name),
        resultSet.getString(InterviewGuideTable.employee_name.name),
        resultSet.getString(InterviewGuideTable.referral_date.name)?.let {
            LocalDateTime.parse(it.replace(" ", "T")) },
        resultSet.getString(InterviewGuideTable.referral_notes.name),
        resultSet.getBoolean(InterviewGuideTable.interview_complete.name),
        resultSet.getString(InterviewGuideTable.interview_notes.name),
        resultSet.getInt(InterviewGuideTable.wo_number.name),
        resultSet.getInt(InterviewGuideTable.emp_notes_id.name),
        resultSet.getInt(InterviewGuideTable.client_notes_id.name)
    )
}
