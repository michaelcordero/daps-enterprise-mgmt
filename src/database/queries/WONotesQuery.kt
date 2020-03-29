package database.queries

import database.tables.WONotesTable
import model.WONotes
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

interface WONotesQuery {
    // Abstract property initialized by LocalDataService
    val db: Database

    /**
     * Create
     */
    fun createWONotes(won: WONotes) {
        transaction (db) {
            WONotesTable.insert {
                // id will be auto-incremented
                it[wo_number] = won.wo_number
                it[note_date] = won.note_date
                it[initial] = won.initial
                it[comments] = won.comments
                it[followup_date] = won.followup_date
            }
        }
    }

    fun insertWONotes(won: WONotes) {
        transaction (db) {
            WONotesTable.insert {
                it[id] = won.id
                it[wo_number] = won.wo_number
                it[note_date] = won.note_date
                it[initial] = won.initial
                it[comments] = won.comments
                it[followup_date] = won.followup_date
            }
        }
    }

    /**
     * Read
     */

    fun allWONotes(): List<WONotes> {
        return transaction (db) {
            WONotesTable.selectAll().toList()
        }.map {
            WONotes(
                it[WONotesTable.id],
                it[WONotesTable.wo_number],
                it[WONotesTable.note_date],
                it[WONotesTable.initial],
                it[WONotesTable.comments],
                it[WONotesTable.followup_date]
            )
        }
    }

    fun readWONotes(id: Int): WONotes {
        return transaction (db) {
            WONotesTable.select {
                WONotesTable.id.eq(id)
            }
        }.map {
            WONotes(
                it[WONotesTable.id],
                it[WONotesTable.wo_number],
                it[WONotesTable.note_date],
                it[WONotesTable.initial],
                it[WONotesTable.comments],
                it[WONotesTable.followup_date]
            )
        }.first()
    }

    /**
     * Update
     */

    fun updateWONotes(won: WONotes) {
        transaction (db) {
            WONotesTable.update({
                WONotesTable.id.eq(won.id)
            }) {
                it[id] = won.id
                it[wo_number] = won.wo_number
                it[note_date] = won.note_date
                it[initial] = won.initial
                it[comments] = won.comments
                it[followup_date] = won.followup_date
            }
        }
    }

    /**
     * Delete
     */

    fun deleteWONote(won: WONotes) {
        transaction (db) {
            WONotesTable.deleteWhere {
                WONotesTable.id.eq(won.id)
            }
        }
    }

    fun deleteAllWONotes() {
        transaction (db) {
            WONotesTable.deleteAll()
        }
    }

}
