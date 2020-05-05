package model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import database.tables.InterviewGuideTable
import java.io.Serializable
import java.sql.ResultSet
import java.sql.Timestamp

data class InterviewGuide
@JsonCreator constructor(
    val id: Int?, @JsonProperty(value = "client_num", required = true)
    val client_num: Int, val client_contact: String?,
    @JsonProperty(value = "employee_num", required = true)
    val employee_num: Int, val employee_name: String?,
    val referral_date: Timestamp?, val referral_notes: String?,
    val interview_complete: Boolean?, val interview_notes: String?,
    val wo_number: Int?, val emp_notes_id: Int?, val client_notes_id: Int?
) : Serializable {
    constructor(resultSet: ResultSet) : this(
        resultSet.getInt(InterviewGuideTable.ID.name),
        resultSet.getInt(InterviewGuideTable.client_num.name),
        resultSet.getString(InterviewGuideTable.client_contact.name),
        resultSet.getInt(InterviewGuideTable.employee_num.name),
        resultSet.getString(InterviewGuideTable.employee_name.name),
        resultSet.getString(InterviewGuideTable.referral_date.name)?.let { Timestamp.valueOf(it) },
        resultSet.getString(InterviewGuideTable.referral_notes.name),
        resultSet.getBoolean(InterviewGuideTable.interview_complete.name),
        resultSet.getString(InterviewGuideTable.interview_notes.name),
        resultSet.getInt(InterviewGuideTable.wo_number.name),
        resultSet.getInt(InterviewGuideTable.emp_notes_id.name),
        resultSet.getInt(InterviewGuideTable.client_notes_id.name)
    )
}
