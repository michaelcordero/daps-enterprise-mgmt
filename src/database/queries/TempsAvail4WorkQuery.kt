package database.queries

import database.tables.TempsAvail4WorkTable
import model.TempsAvail4Work
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

interface TempsAvail4WorkQuery {
    // Abstract property initialized by LocalDataQuery
    val db: Database

    /**
     * Create
     */

    fun createTempAvail4Work(taw: TempsAvail4Work): Int {
        return transaction (db) {
            TempsAvail4WorkTable.insert {
                // rec-num will be auto-incremented
                it[emp_num] = taw.emp_num
                it[date_can_work] = taw.date_can_work
            }
        } get TempsAvail4WorkTable.rec_num
    }

    fun insertTempAvail4Work(taw: TempsAvail4Work) {
        transaction (db) {
            TempsAvail4WorkTable.insert {
                it[rec_num] = taw.rec_num
                it[emp_num] = taw.emp_num
                it[date_can_work] = taw.date_can_work
            }
        }
    }


    /**
     * Read
     */

    fun allTempsAvail4Work(): List<TempsAvail4Work> {
        return transaction (db) {
            TempsAvail4WorkTable.selectAll().toList()
        }.map {
            TempsAvail4Work(
                it[TempsAvail4WorkTable.rec_num],
                it[TempsAvail4WorkTable.emp_num],
                it[TempsAvail4WorkTable.date_can_work]
            )
        }
    }

    fun readTempAvail4Work(rec_num: Int): TempsAvail4Work {
        return transaction (db) {
            TempsAvail4WorkTable.select {
                TempsAvail4WorkTable.rec_num.eq(rec_num)}
        }.map {
            TempsAvail4Work(
                it[TempsAvail4WorkTable.rec_num],
                it[TempsAvail4WorkTable.emp_num],
                it[TempsAvail4WorkTable.date_can_work]
            )
        }.first()
    }

    /**
     * Update
     */
    fun updateTempAvail4Work(taw: TempsAvail4Work) {
        transaction (db) {
            TempsAvail4WorkTable.update({
                TempsAvail4WorkTable.rec_num.eq(taw.rec_num)
            }) {
                it[emp_num] = taw.emp_num
                it[date_can_work] = taw.date_can_work
            }
        }
    }

    /**
     * Delete
     */

    fun deleteTempAvail4Work(taw: TempsAvail4Work) {
        transaction (db) {
            TempsAvail4WorkTable.deleteWhere {
                TempsAvail4WorkTable.rec_num.eq(taw.rec_num)
            }
        }
    }

    fun deleteAllTempAvail4Work() {
        transaction (db) {
            TempsAvail4WorkTable.deleteAll()
        }
    }
}



