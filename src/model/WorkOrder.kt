package model

import database.tables.WorkOrderTable
import java.io.Serializable
import java.sql.ResultSet
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
                     val confirmed: Boolean?) : Serializable {
    constructor(result_set: ResultSet) : this (
        result_set.getInt(WorkOrderTable.wo_number.name),
        result_set.getInt(WorkOrderTable.client_num.name),
        result_set.getInt(WorkOrderTable.emp_num.name),
        result_set.getString(WorkOrderTable.temp_perm.name),
        result_set.getString(WorkOrderTable.filled_date.name)?.let {
            LocalDateTime.parse(it.replace(" ", "T"))
        },
        result_set.getDouble(WorkOrderTable.filled_rate.name),
        result_set.getString(WorkOrderTable.start_date.name)?.let {
            LocalDateTime.parse(it.replace(" ", "T"))
        },
        result_set.getString(WorkOrderTable.start_time.name)?.let {
            LocalDateTime.parse(it.replace(" ", "T"))
        },
        result_set.getString(WorkOrderTable.end_time.name)?.let {
            LocalDateTime.parse(it.replace( " ", "T"))
        },
        result_set.getString(WorkOrderTable.services_category.name),
        result_set.getString(WorkOrderTable.job_description.name),
        result_set.getString(WorkOrderTable.skills_required.name),
        result_set.getString(WorkOrderTable.work_hours.name),
        result_set.getBoolean(WorkOrderTable.will_train.name),
        result_set.getBoolean(WorkOrderTable.confidential.name),
        result_set.getString(WorkOrderTable.contact_name.name),
        result_set.getBoolean(WorkOrderTable.fees_discussed.name),
        result_set.getString(WorkOrderTable.note.name),
        result_set.getString(WorkOrderTable.entered_by.name),
        result_set.getString(WorkOrderTable.entered_date.name)?.let {
            LocalDateTime.parse(it.replace(" ","T"))
        },
        result_set.getBoolean(WorkOrderTable.post.name),
        result_set.getBoolean(WorkOrderTable.active.name),
        result_set.getString(WorkOrderTable.left_message.name)?.let {
            LocalDateTime.parse(it.replace(" ", "T"))
        },
        result_set.getBoolean(WorkOrderTable.confirmed.name)
    )
}
