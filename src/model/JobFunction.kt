package model

import database.tables.JobFunctionTable
import java.io.Serializable
import java.sql.ResultSet

data class JobFunction(val dentist: String?, val hygienist: String?,
                       val assistant: String?, val technician: String?,
                       val ofcmanager: String?, val receptionist: String?,
                       val sales: String?, val other: String? ) : Serializable {
    constructor(resultSet: ResultSet) : this (
        resultSet.getString(JobFunctionTable.dentist.name),
        resultSet.getString(JobFunctionTable.hygienist.name),
        resultSet.getString(JobFunctionTable.assistant.name),
        resultSet.getString(JobFunctionTable.technician.name),
        resultSet.getString(JobFunctionTable.ofcmanager.name),
        resultSet.getString(JobFunctionTable.receptionist.name),
        resultSet.getString(JobFunctionTable.sales.name),
        resultSet.getString(JobFunctionTable.other.name)
    )
}
