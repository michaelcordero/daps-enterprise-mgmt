package database.queries

import database.tables.DAPSStaffMessagesTable
import model.DAPSStaffMessage
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

interface DAPSStaffMessagesQuery {
    // Abstract property initialized by LocalDataQuery
    val db: Database

    /**
     * Create
     */
    fun createDAPSStaffMessages(dsm: DAPSStaffMessage): Int {
        return transaction (db) {
            DAPSStaffMessagesTable.insert {
                it[memo_date] = dsm.memo_date
                it[entered_by] = dsm.entered_by
                it[intended_for] = dsm.intended_for
                it[message] = dsm.message
                // staff messagekey auto incremented
            } get DAPSStaffMessagesTable.staff_messages_key
        }
    }

    fun insertDAPSStaffMessages(dsm: DAPSStaffMessage) {
        transaction (db) {
            DAPSStaffMessagesTable.insert {
                it[staff_messages_key] = dsm.staff_messages_key!!
                it[memo_date] = dsm.memo_date
                it[entered_by] = dsm.entered_by
                it[intended_for] = dsm.intended_for
                it[message] = dsm.message
            }
        }
    }

    /**
     * Read
     */
    fun allDAPSStaffMessages() : List<DAPSStaffMessage> {
        return transaction (db) {
            DAPSStaffMessagesTable.selectAll().toList()
        }.map {
            DAPSStaffMessage(
                it[DAPSStaffMessagesTable.memo_date],
                it[DAPSStaffMessagesTable.entered_by],
                it[DAPSStaffMessagesTable.intended_for],
                it[DAPSStaffMessagesTable.message],
                it[DAPSStaffMessagesTable.staff_messages_key]
            )
        }
    }

    /**
     * Update
     */

    fun updateDAPSStaffMessages(dsm: DAPSStaffMessage): Int {
        return transaction (db) {
            DAPSStaffMessagesTable.update({
                DAPSStaffMessagesTable.staff_messages_key.eq(dsm.staff_messages_key!!)
            })
            {
                it[memo_date] = dsm.memo_date
                it[entered_by] = dsm.entered_by
                it[intended_for] = dsm.intended_for
                it[message] = dsm.message
                it[staff_messages_key] = dsm.staff_messages_key!!
            }
        }
    }

    /**
     * Delete
     */

    fun deleteDAPSStaffMessages(staff_messages_key: Int): Int {
        return transaction (db) {
            DAPSStaffMessagesTable.deleteWhere {
                DAPSStaffMessagesTable.staff_messages_key.eq(staff_messages_key)
            }
        }
    }

}
