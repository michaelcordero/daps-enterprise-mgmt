package database.facades

import database.tables.BillingTable
import model.Billing
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Timestamp

interface BillingData {
    // Abstract property intended to be overridden
    val db: Database

    /**
     * Create
     */
    fun createBilling(billing: Billing){
        transaction(db) {
            BillingTable.insert {
                it[counter] = billing.counter
                it[client_num] = billing.client_num
                it[employee_num] = billing.employee_num
                it[wdate] = billing.wdate?.toInstant()
                it[hours] = billing.hours
                it[start_time] = billing.start_time?.toInstant()
                it[end_time] = billing.end_time?.toInstant()
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
                it[assign_date] = billing.assigned_date?.toInstant()
                it[assigned_by] = billing.assigned_by
                it[service_category] = billing.service_category
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
                it[wdate] = billing.wdate?.toInstant()
                it[hours] = billing.hours
                it[start_time] = billing.start_time?.toInstant()
                it[end_time] = billing.end_time?.toInstant()
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
                it[assign_date] = billing.assigned_date?.toInstant()
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
            Timestamp.from(it[BillingTable.wdate]),
            it[BillingTable.hours],
            Timestamp.from(it[BillingTable.start_time]),
            Timestamp.from(it[BillingTable.end_time]),
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
            Timestamp.from(it[BillingTable.assign_date]),
            it[BillingTable.assigned_by],
            it[BillingTable.service_category]
        )
    }
}
