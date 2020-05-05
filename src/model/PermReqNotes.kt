package model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import database.tables.PermReqNotesTable
import java.io.Serializable
import java.sql.ResultSet
import java.sql.Timestamp

data class PermReqNotes
@JsonCreator constructor(
    val id: Int?,
    @JsonProperty(value = "emp_num", required = true)
    val emp_num: Int, val desired_location: String?,
    val start_date: Timestamp?, val fulltime: Boolean?,
    val desired_days: String?, val special_requests: String?,
    val not_interested: String?
) : Serializable {
    constructor(resultSet: ResultSet) : this(
        resultSet.getInt(PermReqNotesTable.ID.name),
        resultSet.getInt(PermReqNotesTable.emp_num.name),
        resultSet.getString(PermReqNotesTable.desired_location.name),
        resultSet.getString(PermReqNotesTable.start_date.name)?.let { Timestamp.valueOf(it) },
        resultSet.getBoolean(PermReqNotesTable.fulltime.name),
        resultSet.getString(PermReqNotesTable.desired_days.name),
        resultSet.getString(PermReqNotesTable.special_requests.name),
        resultSet.getString(PermReqNotesTable.not_interested.name)
    )
}

