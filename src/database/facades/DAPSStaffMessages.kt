package database.facades

import database.tables.DAPSStaffMessagesTable
import model.DAPSStaffMessages
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

interface DAPSStaffMessages {
    // Abstract property initialized by LocalDataService
    val db: Database

    /**
     * Create
     */
    fun createDAPSStaffMessages(dsm: DAPSStaffMessages) {
        transaction (db) {
            DAPSStaffMessagesTable.insert {
                it[memo_date] = dsm.memo_date
                it[entered_by] = dsm.entered_by
                it[intended_for] = dsm.intended_for
                it[message] = dsm.message
                // staff messagekey auto incremented
            }
        }
    }

    fun insertDAPSStaffMessages(dsm: DAPSStaffMessages) {
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
    fun allDAPSStaffMessagesTable() : List<DAPSStaffMessages> {
        return transaction (db) {
            DAPSStaffMessagesTable.selectAll().toList()
        }.map {
            DAPSStaffMessages(
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

    fun updateDAPSStaffMessages(dsm: DAPSStaffMessages) {
        transaction (db) {
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

    fun deleteDAPSStaffMessages(dsm: DAPSStaffMessages) {
        transaction (db) {
            DAPSStaffMessagesTable.deleteWhere {
                DAPSStaffMessagesTable.staff_messages_key.eq(dsm.staff_messages_key!!)
            }
        }
    }

}
