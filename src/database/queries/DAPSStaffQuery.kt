package database.queries

import database.tables.DAPSStaffTable
import model.DAPSStaff
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

interface DAPSStaffQuery {
    // Abstract property initialized by LocalDataQuery
    val db: Database

    /**
     * Create
     */
    fun insertDAPSStaff(ds: DAPSStaff) {
        return transaction (db) {
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

    fun updateDAPSStaff(ds: DAPSStaff): Int {
        return transaction (db) {
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

    fun deleteDAPSStaff(ds: DAPSStaff): Int {
        return transaction (db) {
            DAPSStaffTable.deleteWhere {
                DAPSStaffTable.initial.eq(ds.initial!!)
            }
        }
    }


}
