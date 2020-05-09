package model

import com.fasterxml.jackson.annotation.JsonCreator
import database.tables.JobFunctionDropDownTable
import java.io.Serializable
import java.sql.ResultSet

data class JobFunction
@JsonCreator constructor(
    val dentist: String?, val hygienist: String?,
    val assistant: String?, val technician: String?,
    val ofcmanager: String?, val receptionist: String?,
    val sales: String?, val other: String?
) : Serializable {
    constructor(resultSet: ResultSet) : this(
        resultSet.getString(JobFunctionDropDownTable.dentist.name),
        resultSet.getString(JobFunctionDropDownTable.hygienist.name),
        resultSet.getString(JobFunctionDropDownTable.assistant.name),
        resultSet.getString(JobFunctionDropDownTable.technician.name),
        resultSet.getString(JobFunctionDropDownTable.ofcmanager.name),
        resultSet.getString(JobFunctionDropDownTable.receptionist.name),
        resultSet.getString(JobFunctionDropDownTable.sales.name),
        resultSet.getString(JobFunctionDropDownTable.other.name)
    )
}
