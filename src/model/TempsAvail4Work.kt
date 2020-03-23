package model

import database.tables.TempsAvail4WorkTable
import java.io.Serializable
import java.sql.ResultSet
import java.sql.Timestamp

data class TempsAvail4Work(val rec_num: Int, val emp_num: Int, val date_can_work: Timestamp?) : Serializable {
    constructor(result_set: ResultSet) : this (
        result_set.getInt(TempsAvail4WorkTable.rec_num.name),
        result_set.getInt(TempsAvail4WorkTable.emp_num.name),
        result_set.getString(TempsAvail4WorkTable.date_can_work.name)?.let { Timestamp.valueOf(it) }
    )
}
