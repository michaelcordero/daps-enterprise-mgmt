package database.queries

import database.tables.ClientPermNotesTable
import model.ClientPermNote
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

interface ClientPermNotesQuery {
    // Abstract property initialized by LocalDataQuery
val db: Database

    /**
     * Create
     */
    fun createClientPermNotes(cpn: ClientPermNote): Int {
        return transaction (db) {
            ClientPermNotesTable.insert {
//                it[id] = cpn.id auto-increment
                it[client_num] = cpn.client_num
                it[wo_num] = cpn.wo_num
                it[not_interested] = cpn.not_interested
                it[staffname] = cpn.staff_name
            }
        } get ClientPermNotesTable.ID
    }

    fun insertClientPermNotes(cpn: ClientPermNote) {
        transaction (db) {
            ClientPermNotesTable.insert {
                it[ID] = cpn.id
                it[client_num] = cpn.client_num
                it[wo_num] = cpn.wo_num
                it[not_interested] = cpn.not_interested
                it[staffname] = cpn.staff_name
            }
        }
    }

    /**
     * Read
     */

    fun allClientPermNotes() : List<ClientPermNote> {
        return transaction(db) {
            ClientPermNotesTable.selectAll().toList()
        }.map {
            ClientPermNote(
                it[ClientPermNotesTable.ID],
                it[ClientPermNotesTable.client_num],
                it[ClientPermNotesTable.wo_num],
                it[ClientPermNotesTable.not_interested],
                it[ClientPermNotesTable.staffname]
            )
        }
    }

    /**
     * Update
     */

    fun updateClientPermNote(cpn: ClientPermNote): Int {
        return transaction(db) {
            ClientPermNotesTable.update({
                ClientPermNotesTable.ID.eq(cpn.id) and ClientPermNotesTable.client_num.eq(cpn.client_num)
            }) {
                it[ID] = cpn.id
                it[client_num] = cpn.client_num
                it[wo_num] = cpn.wo_num
                it[not_interested] = cpn.not_interested
                it[staffname] = cpn.staff_name
            }
        }
    }

    /**
     * Delete
     */

    fun deleteClientPermNote(id: Int): Int {
       return transaction (db) {
            ClientPermNotesTable.deleteWhere {
                ClientPermNotesTable.ID.eq(id)
            }
        }
    }
}
