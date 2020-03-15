package database.facades

import database.tables.BillingTable
import model.Billing
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement

interface BillingData {
    // Abstract property intended to be overridden
    val db: Database

    /**
     * Create
     */
    fun createBilling(billing: Billing){
        db.transaction {
            BillingTable.insert {
                write(it, billing)
            }
        }
    }

    /**
     * Read
     */

    fun billingByClient(client_num: Int): List<Billing> {
        return db.transaction {
            BillingTable.select {
                BillingTable.client_num.eq(client_num)
            }.mapNotNull {
                read(it)
            }
        }
    }

    fun billingByEmp(emp_num: Int) : List<Billing> {
        return db.transaction {
            BillingTable.select {
                BillingTable.employee_num.eq(emp_num)
            }.mapNotNull {
                read(it)
            }
        }
    }

    fun allBilling(): List<Billing> = db.transaction {
        BillingTable.selectAll().toMutableList()
    }.map {
        read(it)
    }

    /**
     * Update
     */

    fun updateBilling(billing: Billing) {
        db.transaction {
            BillingTable.update({
                (BillingTable.client_num.eq(billing.client_num) and BillingTable.employee_num.eq(billing.employee_num)) and
                        BillingTable.counter.eq(billing.counter)
            }) {
                write(it,billing)
            }
        }
    }

    /**
     * Delete
     */

    fun deleteBilling(billing: Billing) {
        db.transaction {
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
            it[BillingTable.hours].toDouble(),
            it[BillingTable.start_time],
            it[BillingTable.end_time],
            it[BillingTable.daps_fee].toDouble(),
            it[BillingTable.total_fee].toDouble(),
            it[BillingTable.worktype],
            it[BillingTable.work_order_num],
            it[BillingTable.open],
            it[BillingTable.pmt1],
            it[BillingTable.apamt1].toDouble(),
            it[BillingTable.pmt2],
            it[BillingTable.apamt2].toDouble(),
            it[BillingTable.notesp],
            it[BillingTable.pending],
            it[BillingTable.assign_date],
            it[BillingTable.assigned_by],
            it[BillingTable.service_category]
        )
    }

    fun BillingTable.write(
        it: InsertStatement,
        billing: Billing
    ) {
        it[counter] = billing.counter
        it[client_num] = billing.client_num
        it[employee_num] = billing.employee_num
        it[wdate] = billing.wdate
        it[hours] = billing.hours.toBigDecimal()
        it[start_time] = billing.start_time
        it[end_time] = billing.end_time
        it[daps_fee] = billing.daps_fee?.toBigDecimal()
        it[total_fee] = billing.total_fee?.toBigDecimal()
        it[worktype] = billing.worktype
        it[work_order_num] = billing.work_order_num
        it[open] = billing.open
        it[pmt1] = billing.pmt1
        it[apamt1] = billing.apamt1?.toBigDecimal()
        it[pmt2] = billing.pmt2
        it[apamt2] = billing.apamt2?.toBigDecimal()
        it[notesp] = billing.notesp
        it[pending] = billing.pending
        it[assign_date] = billing.assigned_date
        it[assigned_by] = billing.assigned_by
        it[service_category] = billing.service_category
    }

    fun BillingTable.write(
        it: UpdateStatement,
        billing: Billing
    ) {
        it[counter] = billing.counter
        it[client_num] = billing.client_num
        it[employee_num] = billing.employee_num
        it[wdate] = billing.wdate
        it[hours] = billing.hours.toBigDecimal()
        it[start_time] = billing.start_time
        it[end_time] = billing.end_time
        it[daps_fee] = billing.daps_fee?.toBigDecimal()
        it[total_fee] = billing.total_fee?.toBigDecimal()
        it[worktype] = billing.worktype
        it[work_order_num] = billing.work_order_num
        it[open] = billing.open
        it[pmt1] = billing.pmt1
        it[apamt1] = billing.apamt1?.toBigDecimal()
        it[pmt2] = billing.pmt2
        it[apamt2] = billing.apamt2?.toBigDecimal()
        it[notesp] = billing.notesp
        it[pending] = billing.pending
        it[assign_date] = billing.assigned_date
        it[assigned_by] = billing.assigned_by
        it[service_category] = billing.service_category
    }
}
