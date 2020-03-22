package model

import database.tables.PasteErrorsTable
import java.io.Serializable
import java.sql.ResultSet
import java.time.LocalDateTime

data class PasteErrors(val client_num: Int?, val pmt_type: String?,
                       val ref_num: String?, val pmt_date: LocalDateTime?,
                       val amount: Double?) : Serializable {
    constructor(result_set: ResultSet) : this (
        result_set.getInt(PasteErrorsTable.client_num.name),
        result_set.getString(PasteErrorsTable.pmt_type.name),
        result_set.getString(PasteErrorsTable.ref_num.name),
        result_set.getString(PasteErrorsTable.pmt_date.name)?.let {
            LocalDateTime.parse(it.replace(" ", "T"))
        },
        result_set.getDouble(PasteErrorsTable.amount.name)
    )
}
