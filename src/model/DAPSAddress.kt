package model

import database.tables.DAPSAddressTable
import java.io.Serializable
import java.sql.ResultSet

data class DAPSAddress(val mailing_list_id: Int?, val office: String?,
                       val address1: String?, val address2: String?,
                       val city: String?, val state: String?, val zip_code: String?) : Serializable {
    constructor(resultSet: ResultSet) : this (
        resultSet.getInt(DAPSAddressTable.mailinglist_id.name),
        resultSet.getString(DAPSAddressTable.office.name),
        resultSet.getString(DAPSAddressTable.address1.name),
        resultSet.getString(DAPSAddressTable.address2.name),
        resultSet.getString(DAPSAddressTable.city.name),
        resultSet.getString(DAPSAddressTable.state.name),
        resultSet.getString(DAPSAddressTable.zipcode.name)
    )

}
