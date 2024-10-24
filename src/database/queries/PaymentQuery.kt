package database.queries

import database.tables.PaymentTable
import model.Payment
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

interface PaymentQuery {
    // Abstract property initialized by LocalDataQuery
    val db: Database

    /**
     * Create
     */

    fun insertPayment(p: Payment) {
        transaction (db) {
            PaymentTable.insert {
                it[client_num] = p.client_num
                it[pmt_type] = p.pmt_type
                it[ref_num] = p.ref_num
                it[pmt_date] = p.pmt_date
                it[amount] = p.amount
            }
        }
    }

    /**
     * Read
     */

    fun allPayments() : List<Payment> {
        return transaction (db) {
            PaymentTable.selectAll().toList()
        }.map {
            Payment(
                it[PaymentTable.client_num],
                it[PaymentTable.pmt_type],
                it[PaymentTable.ref_num],
                it[PaymentTable.pmt_date],
                it[PaymentTable.amount]
            )
        }
    }
    /**
     * Update
     */

    fun updatePayment(p: Payment): Int {
        return transaction(db) {
            PaymentTable.update({
                PaymentTable.ref_num.eq(p.ref_num)
            }) {
                it[client_num] = p.client_num
                it[ref_num] = p.ref_num
                it[pmt_type] = p.pmt_type
                it[pmt_date] = p.pmt_date
                it[amount] = p.amount
            }
        }
    }

    /**
     * Delete
     */

    fun deletePayment(p: Payment): Int {
        return transaction (db) {
            PaymentTable.deleteWhere {
                PaymentTable.client_num.eq(p.client_num) and
                        PaymentTable.ref_num.eq(p.ref_num)
            }
        }
    }
}
