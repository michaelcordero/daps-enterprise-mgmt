package model

import database.tables.ClientPermNotesTable
import java.io.Serializable
import java.sql.ResultSet

data class ClientPermNotes(val id: Int, val client_num: Int?, val wo_num: Int?,
                           val not_interested: String?, val staff_name: String?) : Serializable {
    constructor(result_set: ResultSet) : this (
        result_set.getInt(ClientPermNotesTable.id.name),
        result_set.getInt(ClientPermNotesTable.client_num.name),
        result_set.getInt(ClientPermNotesTable.wo_num.name),
        result_set.getString(ClientPermNotesTable.not_interested.name),
        result_set.getString(ClientPermNotesTable.staffname.name)
    )
}
