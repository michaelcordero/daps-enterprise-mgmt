package database.facades

import database.tables.ClientNotesTable
import model.ClientNotes
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

interface ClientNotesData {
    // Abstract property initialized by LocalDataService
val db: Database
    /**
     * Create
     */
    fun createClientNotes(cn: ClientNotes) {
        transaction (db) {
            ClientNotesTable.insert {
                it[clientnotekey] = cn.client_note_key
                it[client_num] = cn.client_num
                it[notedate] = cn.notedate
                it[initial] = cn.initial
                it[note] = cn.note
            }
        }
    }

    /**
     * Read
     */

    fun allClientNotes(): List<ClientNotes> {
        return transaction (db) {
            ClientNotesTable.selectAll().toMutableList()
        }.map {
            ClientNotes(
                it[ClientNotesTable.client_num],
                it[ClientNotesTable.notedate],
                it[ClientNotesTable.initial],
                it[ClientNotesTable.note],
                it[ClientNotesTable.clientnotekey]
            )
        }
    }

    /**
     * Update
     */

    fun updateClientNotes(cn: ClientNotes) {
        transaction (db) {
            ClientNotesTable.update({
                ClientNotesTable.client_num.eq(cn.client_num) and
                        ClientNotesTable.clientnotekey.eq(cn.client_note_key)
            }) {
                it[client_num] = cn.client_num
                it[notedate] = cn.notedate
                it[initial] = cn.initial
                it[note] = cn.note
                it[clientnotekey] = cn.client_note_key
            }
        }
    }

    /**
     * Delete
     */

    fun deleteClientNote(cn: ClientNotes) {
        transaction (db) {
            ClientNotesTable.deleteWhere {
                ClientNotesTable.client_num.eq(cn.client_num) and
                        ClientNotesTable.clientnotekey.eq(cn.client_note_key)
            }
        }
    }
}
