package database.queries

import database.tables.WorkOrderTable
import model.WorkOrder
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

interface WorkOrderQuery {
    // Abstract property initialized by LocalDataQuery
    val db: Database

    /**
     * Create
     */
    fun createWorkOrder(wo: WorkOrder): Int {
        return transaction (db) {
            WorkOrderTable.insert {
                // wo_number will be auto-incremented
                it[client_num] = wo.client_num
                it[emp_num] = wo.emp_num
                it[temp_perm] = wo.temp_perm
                it[filled_date] = wo.filled_date
                it[filled_rate] = wo.filled_rate
                it[start_date] = wo.start_date
                it[start_time] = wo.start_time
                it[end_time] = wo.end_time
                it[services_category] = wo.services_category
                it[job_description] = wo.job_description
                it[skills_required] = wo.skills_required
                it[work_hours] = wo.work_hours
                it[will_train] = wo.will_train
                it[confidential] = wo.confidential
                it[contact_name] = wo.contact_name
                it[fees_discussed] = wo.fees_discussed
                it[note] = wo.note
                it[entered_by] = wo.entered_by
                it[entered_date] = wo.entered_date
                it[post] = wo.post
                it[active] = wo.active
                it[left_message] = wo.left_message
                it[confirmed] = wo.confirmed
            } get WorkOrderTable.wo_number
        }
    }

    fun insertWorkOrder(wo: WorkOrder) {
        transaction (db) {
            WorkOrderTable.insert {
                it[wo_number] = wo.wo_number
                it[client_num] = wo.client_num
                it[emp_num] = wo.emp_num
                it[temp_perm] = wo.temp_perm
                it[filled_date] = wo.filled_date
                it[filled_rate] = wo.filled_rate
                it[start_date] = wo.start_date
                it[start_time] = wo.start_time
                it[end_time] = wo.end_time
                it[services_category] = wo.services_category
                it[job_description] = wo.job_description
                it[skills_required] = wo.skills_required
                it[work_hours] = wo.work_hours
                it[will_train] = wo.will_train
                it[confidential] = wo.confidential
                it[contact_name] = wo.contact_name
                it[fees_discussed] = wo.fees_discussed
                it[note] = wo.note
                it[entered_by] = wo.entered_by
                it[entered_date] = wo.entered_date
                it[post] = wo.post
                it[active] = wo.active
                it[left_message] = wo.left_message
                it[confirmed] = wo.confirmed
            }
        }
    }

    /**
     * Read
     */
    fun allWorkOrders(): List<WorkOrder> {
        return transaction (db) {
            WorkOrderTable.selectAll().toList()
        }.map {
            WorkOrder(
                it[WorkOrderTable.wo_number],
                it[WorkOrderTable.client_num],
                it[WorkOrderTable.emp_num],
                it[WorkOrderTable.temp_perm],
                it[WorkOrderTable.filled_date],
                it[WorkOrderTable.filled_rate],
                it[WorkOrderTable.start_date],
                it[WorkOrderTable.start_time],
                it[WorkOrderTable.end_time],
                it[WorkOrderTable.services_category],
                it[WorkOrderTable.job_description],
                it[WorkOrderTable.skills_required],
                it[WorkOrderTable.work_hours],
                it[WorkOrderTable.will_train],
                it[WorkOrderTable.confidential],
                it[WorkOrderTable.contact_name],
                it[WorkOrderTable.fees_discussed],
                it[WorkOrderTable.note],
                it[WorkOrderTable.entered_by],
                it[WorkOrderTable.entered_date],
                it[WorkOrderTable.post],
                it[WorkOrderTable.active],
                it[WorkOrderTable.left_message],
                it[WorkOrderTable.confirmed]
            )
        }
    }

    fun readWorkOrder(wo_num: Int): WorkOrder{
        return transaction (db) {
            WorkOrderTable.select {
                WorkOrderTable.wo_number.eq(wo_num)
            }.map {
                WorkOrder(
                    it[WorkOrderTable.wo_number],
                    it[WorkOrderTable.client_num],
                    it[WorkOrderTable.emp_num],
                    it[WorkOrderTable.temp_perm],
                    it[WorkOrderTable.filled_date],
                    it[WorkOrderTable.filled_rate],
                    it[WorkOrderTable.start_date],
                    it[WorkOrderTable.start_time],
                    it[WorkOrderTable.end_time],
                    it[WorkOrderTable.services_category],
                    it[WorkOrderTable.job_description],
                    it[WorkOrderTable.skills_required],
                    it[WorkOrderTable.work_hours],
                    it[WorkOrderTable.will_train],
                    it[WorkOrderTable.confidential],
                    it[WorkOrderTable.contact_name],
                    it[WorkOrderTable.fees_discussed],
                    it[WorkOrderTable.note],
                    it[WorkOrderTable.entered_by],
                    it[WorkOrderTable.entered_date],
                    it[WorkOrderTable.post],
                    it[WorkOrderTable.active],
                    it[WorkOrderTable.left_message],
                    it[WorkOrderTable.confirmed]
                )
            }.first()
        }
    }

    /**
     * Update
     */

    fun updateWorkOrder(wo: WorkOrder) {
        transaction (db) {
            WorkOrderTable.update({
                WorkOrderTable.wo_number.eq(wo.wo_number)
            }) {
                it[wo_number] = wo.wo_number
                it[client_num] = wo.client_num
                it[emp_num] = wo.emp_num
                it[temp_perm] = wo.temp_perm
                it[filled_date] = wo.filled_date
                it[filled_rate] = wo.filled_rate
                it[start_date] = wo.start_date
                it[start_time] = wo.start_time
                it[end_time] = wo.end_time
                it[services_category] = wo.services_category
                it[job_description] = wo.job_description
                it[skills_required] = wo.skills_required
                it[work_hours] = wo.work_hours
                it[will_train] = wo.will_train
                it[confidential] = wo.confidential
                it[contact_name] = wo.contact_name
                it[fees_discussed] = wo.fees_discussed
                it[note] = wo.note
                it[entered_by] = wo.entered_by
                it[entered_date] = wo.entered_date
                it[post] = wo.post
                it[active] = wo.active
                it[left_message] = wo.left_message
                it[confirmed] = wo.confirmed
            }
        }
    }


    /**
     * Delete
     */

    fun deleteWorkOrder(wo: WorkOrder) {
        transaction (db) {
            WorkOrderTable.deleteWhere {
                WorkOrderTable.wo_number.eq(wo.wo_number)
            }
        }
    }

    fun deleteAllWorkOrders() {
        transaction (db) {
            WorkOrderTable.deleteAll()
        }
    }

}
