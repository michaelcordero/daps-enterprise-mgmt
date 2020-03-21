package model

import database.tables.BillTypeTable
import java.io.Serializable
import java.sql.ResultSet

data class BillType(val temp: String?, val perm: String?) : Serializable {
    constructor(resultSet: ResultSet) : this (
        resultSet.getString(BillTypeTable.temp.name),
        resultSet.getString(BillTypeTable.perm.name)
    )
}
