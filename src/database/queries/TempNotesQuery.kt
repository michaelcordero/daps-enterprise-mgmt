package database.queries

import database.tables.TempNotesTable
import model.TempNotes
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

interface TempNotesQuery {
    // Abstract property initialized by LocalDataQuery
    val db: Database

    /**
     * Create
     */
    fun createTempNote(tn: TempNotes): Int {
        return transaction (db) {
            TempNotesTable.insert {
                it[emp_num] = tn.emp_num
                it[note_date] = tn.note_date
                it[initial] = tn.initial
                it[emp_note] = tn.emp_note
                //tempnotekey will be auto-incremented
            } get TempNotesTable.temp_note_key
        }
    }

    fun insertTempNote(tn: TempNotes) {
        transaction (db) {
            TempNotesTable.insert {
                it[emp_num] = tn.emp_num
                it[note_date] = tn.note_date
                it[initial] = tn.initial
                it[emp_note] = tn.emp_note
                it[temp_note_key] = tn.temp_note_key!!
            }
        }
    }


    /**
     * Read
     */

    fun allTempNotes(): List<TempNotes> {
        return transaction (db) {
            TempNotesTable.selectAll().toList()
        }.map {
            TempNotes(
                it[TempNotesTable.emp_num],
                it[TempNotesTable.note_date],
                it[TempNotesTable.initial],
                it[TempNotesTable.emp_note],
                it[TempNotesTable.temp_note_key]
            )
        }
    }

    /**
     * Update
     */

    fun updateTempNote(tn: TempNotes) {
        transaction (db) {
            TempNotesTable.update({
                TempNotesTable.temp_note_key.eq(tn.temp_note_key!!)
            }) {
                it[emp_num] = tn.emp_num
                it[note_date] = tn.note_date
                it[initial] = tn.initial
                it[emp_note] = tn.emp_note
                it[temp_note_key] = tn.temp_note_key!!
            }
        }
    }

    /**
     * Delete
     */

    fun deleteTempNote(tn: TempNotes) {
        transaction (db) {
            TempNotesTable.deleteWhere {
                TempNotesTable.temp_note_key.eq(tn.temp_note_key!!)
            }
        }
    }

}
