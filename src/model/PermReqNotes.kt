package model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import database.tables.PermReqNotesTable
import model.deserializers.BooleanDeserializer
import model.deserializers.LocalDateDeserializer
import model.serializers.BooleanSerializer
import model.serializers.LocalDateSerializer
import java.io.Serializable
import java.sql.ResultSet
import java.sql.Timestamp

data class PermReqNotes
@JsonCreator constructor(
    val id: Int?,
    @JsonProperty(value = "emp_num", required = true)
    val emp_num: Int,
    val desired_location: String?,
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    val start_date: Timestamp?,
    @JsonSerialize(using = BooleanSerializer::class)
    @JsonDeserialize(using = BooleanDeserializer::class)
    val fulltime: Boolean?,
    val desired_days: String?,
    val special_requests: String?,
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

