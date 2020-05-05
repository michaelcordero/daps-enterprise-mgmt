package model

import com.fasterxml.jackson.annotation.JsonCreator
import database.tables.BillTypeDropDownTable
import java.io.Serializable
import java.sql.ResultSet

data class BillType
@JsonCreator constructor(val temp: String?, val perm: String?) : Serializable {
    constructor(resultSet: ResultSet) : this(
        resultSet.getString(BillTypeDropDownTable.temp.name),
        resultSet.getString(BillTypeDropDownTable.perm.name)
    )
}
