package database.queries

import database.tables.DAPSAddressTable
import model.DAPSAddress
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

interface DAPSAddressQuery  {
    // Abstract property initialized by LocalDataQuery
val db: Database

    /**
     * Create
     */
    fun createDAPSAddress(da: DAPSAddress){
        transaction (db) {
            DAPSAddressTable.insert {
//                it[mailinglist_id] = da.mailing_list_id!! auto-increment handles this
                it[office] = da.office
                it[address1] = da.address1
                it[address2] = da.address2
                it[city] = da.city
                it[state] = da.state
                it[zipcode] = da.zip_code
            }
        }
    }

    fun insertDAPSAddress(da: DAPSAddress){
        transaction (db) {
            DAPSAddressTable.insert {
                it[mailinglist_id] = da.mailing_list_id!!
                it[office] = da.office
                it[address1] = da.address1
                it[address2] = da.address2
                it[city] = da.city
                it[state] = da.state
                it[zipcode] = da.zip_code
            }
        }
    }

    /**
     * Read
     */

    fun allDAPSAddress() : List<DAPSAddress> {
        return transaction (db) {
            DAPSAddressTable.selectAll().toList()
        }.map {
            DAPSAddress(
                it[DAPSAddressTable.mailinglist_id],
                it[DAPSAddressTable.office],
                it[DAPSAddressTable.address1],
                it[DAPSAddressTable.address2],
                it[DAPSAddressTable.city],
                it[DAPSAddressTable.state],
                it[DAPSAddressTable.zipcode]
            )
        }
    }

    /**
     * Update
     */

    fun updateDAPSAddress(da: DAPSAddress) {
        transaction (db) {
            DAPSAddressTable.update({ DAPSAddressTable.mailinglist_id.eq(da.mailing_list_id!!)}) {
                it[office] = da.office
                it[address1] = da.address1
                it[address2] = da.address2
                it[city] = da.city
                it[state] = da.state
                it[zipcode] = da.zip_code
            }
        }
    }

    /**
     * Delete
     */

    fun deleteDAPSAddress(da: DAPSAddress) {
        transaction (db) {
            DAPSAddressTable.deleteWhere {
                DAPSAddressTable.mailinglist_id.eq(da.mailing_list_id!!)
            }
        }
    }
}
