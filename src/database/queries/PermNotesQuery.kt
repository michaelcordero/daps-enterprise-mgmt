package database.queries

import database.tables.PermNotesTable
import model.PermNote
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

interface PermNotesQuery {
    // Abstract property initialized by LocalDataQuery
    val db: Database
    /**
     * Create
     */

    fun createPermNotes(pn: PermNote): Int {
        return transaction (db) {
            PermNotesTable.insert {
                // id is auto-incremented
                it[emp_num] = pn.emp_num
                it[note_date] = pn.note_date
                it[initial] = pn.initial
                it[comments] = pn.comments
                it[follow_update] = pn.follow_update
            } get PermNotesTable.ID
        }
    }

    fun insertPermNotes(pn: PermNote) {
        transaction (db) {
            PermNotesTable.insert {
                it[ID] = pn.id!!
                it[emp_num] = pn.emp_num
                it[note_date] = pn.note_date
                it[initial] = pn.initial
                it[comments] = pn.comments
                it[follow_update] = pn.follow_update
            }
        }
    }

    /**
     * Read
     */

    fun allPermNotes(): List<PermNote> {
       return transaction (db) {
            PermNotesTable.selectAll().toList()
        }.map {
           PermNote(
               it[PermNotesTable.ID],
               it[PermNotesTable.emp_num],
               it[PermNotesTable.note_date],
               it[PermNotesTable.initial],
               it[PermNotesTable.comments],
               it[PermNotesTable.follow_update]
           )
       }
    }

    /**
     * Update
     */

    fun updatePermNotes(pn: PermNote): Int {
        return transaction (db) {
            PermNotesTable.update({
                PermNotesTable.ID.eq(pn.id!!)
            }) {
                it[ID] = pn.id!!
                it[emp_num] = pn.emp_num
                it[note_date] = pn.note_date
                it[initial] = pn.initial
                it[comments] = pn.comments
                it[follow_update] = pn.follow_update
            }
        }
    }


    /**
     * Delete
     */

    fun deletePermNotes(id: Int): Int {
        return transaction (db) {
            PermNotesTable.deleteWhere {
                PermNotesTable.ID.eq(id)
            }
        }
    }

}
