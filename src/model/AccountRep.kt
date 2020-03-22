package model

import database.tables.AccountRepDropDownTable
import java.io.Serializable
import java.sql.ResultSet

data class AccountRep(val accountRep: String?) : Serializable {
    constructor(resultSet: ResultSet) : this(resultSet.getString(AccountRepDropDownTable.account_rep.name))
}
