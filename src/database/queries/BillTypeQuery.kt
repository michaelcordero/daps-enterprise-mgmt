package database.queries

import database.tables.BillTypeDropDownTable
import model.BillType
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

interface BillTypeQuery {
    // Abstract property initialized by LocalDataQuery
val db: Database
    /**
     * Create
     */
    fun createBillType(bt: BillType) {
        transaction (db) {
            BillTypeDropDownTable.insert {
                it[temp] = bt.temp
                it[perm] = bt.perm
            }
        }
    }

    /**
     * Read
     */
    fun readAllBillTypes(bt: BillType) : List<BillType> {
        return transaction(db) { BillTypeDropDownTable.selectAll().toMutableList() }
            .map {
                BillType(it[BillTypeDropDownTable.temp], it[BillTypeDropDownTable.perm])
            }
    }

    /**
     * Update
     */
    fun updateBillType(bt: BillType) {
        transaction (db) {
            BillTypeDropDownTable.update {
                it[temp] = bt.temp
                it[perm] = bt.perm
            }
        }
    }

    /**
     * Delete
     */

    fun deleteBillType(bt: BillType) {
        transaction (db) {
            BillTypeDropDownTable.deleteWhere { BillTypeDropDownTable.temp.eq(bt.temp) }
        }
    }
}
