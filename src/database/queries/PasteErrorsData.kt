package database.queries

import database.tables.PasteErrorsTable
import model.PasteErrors
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

interface PasteErrorsData {
    // Abstract property initialized by LocalDataService
    val db: Database

    /**
     * Create
     */
    fun createPasteErrors(pe: PasteErrors) {
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
    fun allPasteErrors(pe: PasteErrors): List<PasteErrors> {
        return transaction(db) {
            PasteErrorsTable.selectAll().toList()
        }.map {
            PasteErrors(
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

    fun updatePasteErrors(pe: PasteErrors) {
        transaction (db) {
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

    fun deletePasteErrors(pe: PasteErrors) {
        transaction (db) {
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
