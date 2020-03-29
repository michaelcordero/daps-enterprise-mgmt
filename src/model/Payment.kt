package model

import database.tables.PaymentTable
import java.io.Serializable
import java.sql.ResultSet
import java.sql.Timestamp

data class Payment(val client_num: Int?, val pmt_type: String?,
                   val ref_num: String?, val pmt_date: Timestamp?,
                   val amount: Double?) : Serializable {
    constructor(result_set: ResultSet) : this (
        result_set.getInt(PaymentTable.client_num.name),
        result_set.getString(PaymentTable.pmt_type.name),
        result_set.getString(PaymentTable.ref_num.name),
        Timestamp.valueOf(result_set.getString(PaymentTable.pmt_date.name)),
        result_set.getDouble(PaymentTable.amount.name)
    )
}
