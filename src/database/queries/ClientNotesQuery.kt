package database.queries

import database.tables.ClientNotesTable
import model.ClientNote
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

interface ClientNotesQuery {
    // Abstract property initialized by LocalDataQuery
val db: Database
    /**
     * Create
     */
    fun createClientNotes(cn: ClientNote): Int {
        return transaction (db) {
            ClientNotesTable.insert {
                // it[clientnotekey] = cn.client_note_key auto-incremented
                it[client_num] = cn.client_num
                it[notedate] = cn.notedate
                it[initial] = cn.initial
                it[note] = cn.note
            }
        } get ClientNotesTable.clientnotekey
    }

    fun insertClientNotes(cn: ClientNote) {
        transaction (db) {
            ClientNotesTable.insert {
                it[clientnotekey] = cn.client_note_key!!
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

    fun allClientNotes(): List<ClientNote> {
        return transaction (db) {
            ClientNotesTable.selectAll().toMutableList()
        }.map {
            ClientNote(
                it[ClientNotesTable.client_num],
                it[ClientNotesTable.notedate],
                it[ClientNotesTable.initial],
                it[ClientNotesTable.note],
                it[ClientNotesTable.clientnotekey]
            )
        }
    }

    // DOES NOT WORK!
    // Traced the bug to ThreadLocalTransactionManager.kt::163. Deferred to Slack channel.
    fun readClientsNotesByClientNum(client_num: Int): List<ClientNote?> = transaction(db) {
            ClientNotesTable.select {
                ClientNotesTable.client_num.eq(client_num)
            }
        }.mapNotNull {
            ClientNote(
                it[ClientNotesTable.client_num],
                it[ClientNotesTable.notedate],
                it[ClientNotesTable.initial],
                it[ClientNotesTable.note],
                it[ClientNotesTable.clientnotekey]
            )
        }

    fun readClientNoteByKey(clientNoteKey: Int): ClientNote? {
        return transaction (db) {
            ClientNotesTable.select {
                ClientNotesTable.clientnotekey.eq(clientNoteKey)
            }.map {
                ClientNote(
                    it[ClientNotesTable.client_num],
                    it[ClientNotesTable.notedate],
                    it[ClientNotesTable.initial],
                    it[ClientNotesTable.note],
                    it[ClientNotesTable.clientnotekey]
                )
            }.singleOrNull()
        }
    }

    fun readClientNotesByInitial(initial: String): List<ClientNote> {
        return transaction (db) {
            ClientNotesTable.select {
                ClientNotesTable.initial.eq(initial)
            }.mapNotNull {
                ClientNote(
                        it[ClientNotesTable.client_num],
                        it[ClientNotesTable.notedate],
                        it[ClientNotesTable.initial],
                        it[ClientNotesTable.note],
                        it[ClientNotesTable.clientnotekey]
                )
            }
        }
    }

    /**
     * Update
     */

    fun updateClientNotes(cn: ClientNote): Int {
        return transaction (db) {
            ClientNotesTable.update({
                ClientNotesTable.client_num.eq(cn.client_num) and
                        ClientNotesTable.clientnotekey.eq(cn.client_note_key!!)
            }) {
                it[client_num] = cn.client_num
                it[notedate] = cn.notedate
                it[initial] = cn.initial
                it[note] = cn.note
                it[clientnotekey] = cn.client_note_key!!
            }
        }
    }

    /**
     * Delete
     */

    fun deleteClientNote(client_note_key: Int): Int {
        return transaction (db) {
            ClientNotesTable.deleteWhere {
                        ClientNotesTable.clientnotekey.eq(client_note_key)
            }
        }
    }
}
