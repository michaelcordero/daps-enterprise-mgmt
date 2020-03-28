package database.facades

import database.tables.ClientNotesTable
import database.tables.ClientPermNotesTable
import model.ClientPermNotes
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

interface ClientPermNotesData {
    // Abstract property initialized by LocalDataService
val db: Database

    /**
     * Create
     */
    fun createClientPermNotes(cpn: ClientPermNotes) {
        transaction (db) {
            ClientPermNotesTable.insert {
//                it[id] = cpn.id auto-increment
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

    fun allClientPermNotes() : List<ClientPermNotes> {
        return transaction(db) {
            ClientNotesTable.selectAll().toList()
        }.map {
            ClientPermNotes(
                it[ClientPermNotesTable.id],
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

    fun updateClientPermNote(cpn: ClientPermNotes) {
        transaction(db) {
            ClientPermNotesTable.update({
                ClientPermNotesTable.id.eq(cpn.id) and ClientPermNotesTable.client_num.eq(cpn.client_num)
            }) {
                it[id] = cpn.id
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

    fun deleteClientPermNote(cpn: ClientPermNotes) {
        transaction (db) {
            ClientPermNotesTable.deleteWhere {
                ClientPermNotesTable.id.eq(cpn.id) and ClientPermNotesTable.client_num.eq(cpn.client_num)
            }
        }
    }
}
