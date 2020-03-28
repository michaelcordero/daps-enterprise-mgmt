package database.facades

import database.tables.DAPSStaffTable
import model.DAPSStaff
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

interface DAPSStaffData {
    // Abstract property initialized by LocalDataService
    val db: Database

    /**
     * Create
     */
    fun createDAPSStaff(ds: DAPSStaff) {
        transaction (db) {
            DAPSStaffTable.insert {
                it[initial] = ds.initial!!
                it[firstname] = ds.firstname
                it[lastname] = ds.lastname
                it[department] = ds.department
            }
        }
    }

    /**
     * Read
     */

    fun allDAPSStaff() : List<DAPSStaff> {
        return transaction (db) {
             DAPSStaffTable.selectAll().toList()
        }.map {
            DAPSStaff(
                it[DAPSStaffTable.initial],
                it[DAPSStaffTable.firstname],
                it[DAPSStaffTable.lastname],
                it[DAPSStaffTable.department]
            )
        }
    }

    /**
     * Update
     */

    fun updateDAPSStaff(ds: DAPSStaff) {
        transaction (db) {
            DAPSStaffTable.update({
                DAPSStaffTable.initial.eq(ds.initial!!)
            }) {
                it[firstname] = ds.firstname
                it[lastname] = ds.lastname
                it[department] = ds.department
            }
        }
    }

    /**
     * Delete
     */

    fun deleteDAPSStaff(ds: DAPSStaff) {
        transaction (db) {
            DAPSStaffTable.deleteWhere {
                DAPSStaffTable.initial.eq(ds.initial!!)
            }
        }
    }


}
