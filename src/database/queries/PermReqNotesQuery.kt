package database.queries

import database.tables.PermReqNotesTable
import model.PermReqNote
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

interface PermReqNotesQuery {
    // Abstract property initialized by LocalDataQuery
    val db: Database

    /**
     * Create
     */
    fun createPermReqNotes(prn: PermReqNote): Int {
       return transaction (db) {
            PermReqNotesTable.insert {
                // id will be auto-incremented
                it[emp_num] = prn.emp_num
                it[desired_location] = prn.desired_location
                it[start_date] = prn.start_date
                it[fulltime] = prn.fulltime
                it[desired_days] = prn.desired_days
                it[special_requests] = prn.special_requests
                it[not_interested] = prn.not_interested
            } get PermReqNotesTable.ID
        }
    }

    fun insertPermReqNotes(prn: PermReqNote) {
        transaction (db) {
            PermReqNotesTable.insert {
                it[ID] = prn.id!!
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

    fun allPermReqNotes(): List<PermReqNote> {
        return transaction (db) {
            PermReqNotesTable.selectAll().toList()
        }.map {
            PermReqNote(
                it[PermReqNotesTable.ID],
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

    fun updatePermReqNote(prn: PermReqNote): Int {
        return transaction (db) {
            PermReqNotesTable.update({
                PermReqNotesTable.ID.eq(prn.id!!)
            }) {
                it[ID] = prn.id!!
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

    fun deletePermReqNote(id: Int): Int {
        return transaction (db) {
            PermReqNotesTable.deleteWhere {
                PermReqNotesTable.ID.eq(id)
            }
        }
    }


}

