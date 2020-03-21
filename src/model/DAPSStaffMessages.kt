package model

import database.tables.DAPSStaffMessagesTable
import java.io.Serializable
import java.sql.ResultSet
import java.time.LocalDateTime


data class DAPSStaffMessages(val memo_date: LocalDateTime?, val entered_by: String?,
                             val intended_for: String?, val message: String?,
                             val staff_messages_key: Int?) : Serializable {
    constructor(resultSet: ResultSet) : this (
        resultSet.getString(DAPSStaffMessagesTable.memo_date.name)?.let {
            LocalDateTime.parse(it.replace(" ", "T")) },
        resultSet.getString(DAPSStaffMessagesTable.entered_by.name),
        resultSet.getString(DAPSStaffMessagesTable.intended_for.name),
        resultSet.getString(DAPSStaffMessagesTable.message.name),
        resultSet.getInt(DAPSStaffMessagesTable.staff_messages_key.name)
    )
}
