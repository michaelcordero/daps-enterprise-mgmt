package database.facades

import database.tables.BillingTable
import model.Billing
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

interface BillingData {
    // Abstract property initialized by LocalDataService
    val db: Database

    /**
     * Create
     */
    fun createBilling(billing: Billing){
        transaction(db) {
            BillingTable.insert {
//                it[counter] = billing.counter auto-increment
                it[client_num] = billing.client_num
                it[employee_num] = billing.employee_num
                it[wdate] = billing.wdate
                it[hours] = billing.hours
                it[start_time] = billing.start_time
                it[end_time] = billing.end_time
                it[daps_fee] = billing.daps_fee
                it[total_fee] = billing.total_fee
                it[worktype] = billing.worktype
                it[work_order_num] = billing.work_order_num
                it[open] = billing.open
                it[pmt1] = billing.pmt1
                it[apamt1] = billing.apamt1
                it[pmt2] = billing.pmt2
                it[apamt2] = billing.apamt2
                it[notesp] = billing.notesp
                it[pending] = billing.pending
                it[assign_date] = billing.assigned_date
                it[assigned_by] = billing.assigned_by
                it[service_category] = billing.service_category
            }
        }
    }

    fun insertBilling(b: Billing) {
        transaction(db) {
            BillingTable.insert {
                it[counter] = b.counter
                it[client_num] = b.client_num
                it[employee_num] = b.employee_num
                it[wdate] = b.wdate
                it[hours] = b.hours
                it[start_time] = b.start_time
                it[end_time] = b.end_time
                it[daps_fee] = b.daps_fee
                it[total_fee] = b.total_fee
                it[worktype] = b.worktype
                it[work_order_num] = b.work_order_num
                it[open] = b.open
                it[pmt1] = b.pmt1
                it[apamt1] = b.apamt1
                it[pmt2] = b.pmt2
                it[apamt2] = b.apamt2
                it[notesp] = b.notesp
                it[pending] = b.pending
                it[assign_date] = b.assigned_date
                it[assigned_by] = b.assigned_by
                it[service_category] = b.service_category
            }
        }
    }

    /**
     * Read
     */

    fun billingByClient(client_num: Int): List<Billing> {
        return transaction(db) {
            BillingTable.select {
                BillingTable.client_num.eq(client_num)
            }.mapNotNull {
                read(it)
            }
        }
    }

    fun billingByEmp(emp_num: Int) : List<Billing> {
        return transaction(db) {
            BillingTable.select {
                BillingTable.employee_num.eq(emp_num)
            }.mapNotNull {
                read(it)
            }
        }
    }

    fun allBilling(): List<Billing> = transaction(db) {
        BillingTable.selectAll().toMutableList()
    }.map {
        read(it)
    }

    /**
     * Update
     */

    fun updateBilling(billing: Billing) {
        transaction(db) {
            BillingTable.update({
                (BillingTable.client_num.eq(billing.client_num) and BillingTable.employee_num.eq(billing.employee_num)) and
                        BillingTable.counter.eq(billing.counter)
            }) {
                it[counter] = billing.counter
                it[client_num] = billing.client_num
                it[employee_num] = billing.employee_num
                it[wdate] = billing.wdate
                it[hours] = billing.hours
                it[start_time] = billing.start_time
                it[end_time] = billing.end_time
                it[daps_fee] = billing.daps_fee
                it[total_fee] = billing.total_fee
                it[worktype] = billing.worktype
                it[work_order_num] = billing.work_order_num
                it[open] = billing.open
                it[pmt1] = billing.pmt1
                it[apamt1] = billing.apamt1
                it[pmt2] = billing.pmt2
                it[apamt2] = billing.apamt2
                it[notesp] = billing.notesp
                it[pending] = billing.pending
                it[assign_date] = billing.assigned_date
                it[assigned_by] = billing.assigned_by
                it[service_category] = billing.service_category
            }
        }
    }

    /**
     * Delete
     */

    fun deleteBilling(billing: Billing) {
        transaction(db) {
            BillingTable.deleteWhere { (BillingTable.client_num.eq(billing.client_num) and BillingTable.employee_num.eq(billing.employee_num)) and
                    BillingTable.counter.eq(billing.counter) }
        }
    }

    fun read(it: ResultRow): Billing {
        return Billing(
            it[BillingTable.counter],
            it[BillingTable.client_num],
            it[BillingTable.employee_num],
            it[BillingTable.wdate],
            it[BillingTable.hours],
            it[BillingTable.start_time],
            it[BillingTable.end_time],
            it[BillingTable.daps_fee],
            it[BillingTable.total_fee],
            it[BillingTable.worktype],
            it[BillingTable.work_order_num],
            it[BillingTable.open],
            it[BillingTable.pmt1],
            it[BillingTable.apamt1],
            it[BillingTable.pmt2],
            it[BillingTable.apamt2],
            it[BillingTable.notesp],
            it[BillingTable.pending],
            it[BillingTable.assign_date],
            it[BillingTable.assigned_by],
            it[BillingTable.service_category]
        )
    }
}
