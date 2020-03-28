package database.queries

import database.tables.PermNotesTable
import model.PermNotes
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

interface PermNotesData {
    // Abstract property initialized by LocalDataService
    val db: Database
    /**
     * Create
     */

    fun createPermNotes(pn: PermNotes) {
        transaction (db) {
            PermNotesTable.insert {
                // id is auto-incremented
                it[emp_num] = pn.emp_num
                it[note_date] = pn.note_date
                it[initial] = pn.initial
                it[comments] = pn.comments
                it[follow_update] = pn.follow_update
            }
        }
    }

    fun insertPermNotes(pn: PermNotes) {
        transaction (db) {
            PermNotesTable.insert {
                it[id] = pn.id!!
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

    fun allPermNotes(pn: PermNotes): List<PermNotes> {
       return transaction (db) {
            PermNotesTable.selectAll().toList()
        }.map {
           PermNotes(
               it[PermNotesTable.id],
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

    fun updatePermNotes(pn: PermNotes) {
        transaction (db) {
            PermNotesTable.update({
                PermNotesTable.id.eq(pn.id!!)
            }) {
                it[id] = pn.id!!
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

    fun deletePermNotes(pn: PermNotes) {
        transaction (db) {
            PermNotesTable.deleteWhere {
                PermNotesTable.id.eq(pn.id!!)
            }
        }
    }

}
