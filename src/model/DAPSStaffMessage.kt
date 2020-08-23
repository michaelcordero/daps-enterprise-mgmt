package model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import database.tables.DAPSStaffMessagesTable
import model.deserializers.LocalDateDeserializer
import model.serializers.LocalDateSerializer
import java.io.Serializable
import java.sql.ResultSet
import java.sql.Timestamp


data class DAPSStaffMessage
    @JsonCreator constructor(
        @JsonSerialize(using = LocalDateSerializer::class)
        @JsonDeserialize(using = LocalDateDeserializer::class)
        val memo_date: Timestamp?,
        val entered_by: String?,
        val intended_for: String?,
        val message: String?,
        val staff_messages_key: Int?) : Serializable {
    constructor(resultSet: ResultSet) : this (
        resultSet.getString(DAPSStaffMessagesTable.memo_date.name)?.let {Timestamp.valueOf(it)},
        resultSet.getString(DAPSStaffMessagesTable.entered_by.name),
        resultSet.getString(DAPSStaffMessagesTable.intended_for.name),
        resultSet.getString(DAPSStaffMessagesTable.message.name),
        resultSet.getInt(DAPSStaffMessagesTable.staff_messages_key.name)
    )
}
