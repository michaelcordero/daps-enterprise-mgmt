package model

import com.fasterxml.jackson.annotation.JsonCreator
import database.tables.DAPSStaffTable
import java.io.Serializable
import java.sql.ResultSet

data class DAPSStaff
@JsonCreator constructor(
    val initial: String?, val firstname: String?,
    val lastname: String?, val department: String?
) : Serializable {
    constructor(resultSet: ResultSet) : this(
        resultSet.getString(DAPSStaffTable.initial.name),
        resultSet.getString(DAPSStaffTable.firstname.name),
        resultSet.getString(DAPSStaffTable.lastname.name),
        resultSet.getString(DAPSStaffTable.department.name)
    )
}
