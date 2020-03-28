package database.queries

import database.tables.PermReqNotesTable
import model.PermReqNotes
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

interface PermReqNotesData {
    // Abstract property initialized by LocalDataService
    val db: Database

    /**
     * Create
     */
    fun createPermReqNotes(prn: PermReqNotes) {
        transaction (db) {
            PermReqNotesTable.insert {
                // id will be auto-incremented
                it[emp_num] = prn.emp_num
                it[desired_location] = prn.desired_location
                it[start_date] = prn.start_date
                it[fulltime] = prn.fulltime
                it[desired_days] = prn.desired_days
                it[special_requests] = prn.special_requests
                it[not_interested] = prn.not_interested
            }
        }
    }

    fun insertPermReqNotes(prn: PermReqNotes) {
        transaction (db) {
            PermReqNotesTable.insert {
                it[id] = prn.id!!
                it[emp_num] = prn.emp_num
                it[desired_location] = prn.desired_location
                it[start_date] = prn.start_date
                it[fulltime] = prn.fulltime
                it[desired_days] = prn.desired_days
                it[special_requests] = prn.special_requests
                it[not_interested] = prn.not_interested
            }
        }
    }

    /**
     * Read
     */

    fun allPermReqNotes(): List<PermReqNotes> {
        return transaction (db) {
            PermReqNotesTable.selectAll().toList()
        }.map {
            PermReqNotes(
                it[PermReqNotesTable.id],
                it[PermReqNotesTable.emp_num],
                it[PermReqNotesTable.desired_location],
                it[PermReqNotesTable.start_date],
                it[PermReqNotesTable.fulltime],
                it[PermReqNotesTable.desired_days],
                it[PermReqNotesTable .special_requests],
                it[PermReqNotesTable.not_interested]
        )
        }
    }

    /**
     * Update
     */

    fun updatePermReqNote(prn: PermReqNotes) {
        transaction (db) {
            PermReqNotesTable.update({
                PermReqNotesTable.id.eq(prn.id!!)
            }) {
                it[id] = prn.id!!
                it[emp_num] = prn.emp_num
                it[desired_location] = prn.desired_location
                it[start_date] = prn.start_date
                it[fulltime] = prn.fulltime
                it[desired_days] = prn.desired_days
                it[special_requests] = prn.special_requests
                it[not_interested] = prn.not_interested
            }
        }
    }

    /**
     * Delete
     */

    fun deletePermReqNote(prn: PermReqNotes) {
        transaction (db) {
            PermReqNotesTable.deleteWhere {
                PermReqNotesTable.id.eq(prn.id!!)
            }
        }
    }


}

