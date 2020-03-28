package database.queries

import database.tables.AccountRepDropDownTable
import model.AccountRep
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

interface AccountRepData {
    // Abstract property initialized by LocalDataService
    val db: Database

    /**
     * Create
     */
    fun createAccountRep(account: AccountRep) {
        transaction(db) {
            AccountRepDropDownTable.insert {
                it[account_rep] = account.accountRep!!
            }
        }
    }

    /**
     * Read
     */

    fun readAccountRep(account: AccountRep): AccountRep {
        val account_list: List<AccountRep> = transaction (db) {
            AccountRepDropDownTable.select {
                AccountRepDropDownTable.account_rep.eq(account.accountRep!!)
            }.mapNotNull {
                AccountRep(it[AccountRepDropDownTable.account_rep])
            }
        }
        return account_list.first()
    }
    /**
     * Update
     */
    fun updateAccountRep(account: AccountRep) {
        transaction (db) {
            AccountRepDropDownTable.update ({
                AccountRepDropDownTable.account_rep.eq(account.accountRep!!)
            }) {
                it[account_rep] = account.accountRep!!
            }
        }
    }

    /**
     * Delete
     */

    fun deleteAccountRep(account: AccountRep) {
        transaction (db) {
            AccountRepDropDownTable.deleteWhere { AccountRepDropDownTable.account_rep.eq(account.accountRep!!) }
        }
    }
}
