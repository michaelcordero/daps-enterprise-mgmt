package database.queries

import database.tables.PasteErrorsTable
import model.PasteError
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

interface PasteErrorsQuery {
    // Abstract property initialized by LocalDataQuery
    val db: Database

    /**
     * Create
     */
    fun insertPasteErrors(pe: PasteError) {
        transaction (db) {
            PasteErrorsTable.insert {
                it[client_num] = pe.client_num
                it[pmt_type] = pe.pmt_type
                it[ref_num] = pe.ref_num
                it[pmt_date] = pe.pmt_date
                it[amount] = pe.amount
            }
        }
    }

    /**
     * Read
     */
    fun allPasteErrors(): List<PasteError> {
        return transaction(db) {
            PasteErrorsTable.selectAll().toList()
        }.map {
            PasteError(
                it[PasteErrorsTable.client_num],
                it[PasteErrorsTable.pmt_type],
                it[PasteErrorsTable.ref_num],
                it[PasteErrorsTable.pmt_date],
                it[PasteErrorsTable.amount]
            )
        }
    }

    /**
     * Update
     */

    fun updatePasteErrors(pe: PasteError): Int {
        return transaction (db) {
            PasteErrorsTable.update ({
                PasteErrorsTable.client_num.eq(pe.client_num) and
                        PasteErrorsTable.ref_num.eq(pe.ref_num)
            }){
                it[client_num] = pe.client_num
                it[ref_num] = pe.ref_num
                it[pmt_type] = pe.pmt_type
                it[pmt_date] = pe.pmt_date
                it[amount] = pe.amount
            }
        }
    }

    /**
     * Delete
     */

    fun deletePasteErrors(pe: PasteError): Int {
        return transaction (db) {
            PasteErrorsTable.deleteWhere {
                PasteErrorsTable.client_num.eq(pe.client_num) and
                        PasteErrorsTable.ref_num.eq(pe.ref_num) and
                        PasteErrorsTable.pmt_type.eq(pe.pmt_type) and
                        PasteErrorsTable.pmt_date.eq(pe.pmt_date) and
                        PasteErrorsTable.amount.eq(pe.amount)
            }
        }
    }
}
