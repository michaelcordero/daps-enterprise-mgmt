package database.queries

import database.tables.WONotesTable
import model.WONotes
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

interface WONotesQuery {
    // Abstract property initialized by LocalDataQuery
    val db: Database

    /**
     * Create
     */
    fun createWONotes(won: WONotes): Int {
        return transaction (db) {
            WONotesTable.insert {
                // id will be auto-incremented
                it[wo_number] = won.wo_number
                it[note_date] = won.note_date
                it[initial] = won.initial
                it[comments] = won.comments
                it[followup_date] = won.followup_date
            }
        } get WONotesTable.ID
    }

    fun insertWONotes(won: WONotes) {
        transaction (db) {
            WONotesTable.insert {
                it[ID] = won.id
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
                it[WONotesTable.ID],
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
                WONotesTable.ID.eq(id)
            }
        }.map {
            WONotes(
                it[WONotesTable.ID],
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

    fun updateWONotes(won: WONotes): Int {
        return transaction (db) {
            WONotesTable.update({
                WONotesTable.ID.eq(won.id)
            }) {
                it[ID] = won.id
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

    fun deleteWONote(id: Int): Int {
       return transaction (db) {
            WONotesTable.deleteWhere {
                WONotesTable.ID.eq(id)
            }
        }
    }

    fun deleteAllWONotes() {
        transaction (db) {
            WONotesTable.deleteAll()
        }
    }

}
