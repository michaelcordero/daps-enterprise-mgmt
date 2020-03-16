package model

import database.tables.BillingTable
import java.io.Serializable
import java.sql.ResultSet
import java.time.LocalDateTime

data class Billing(
    val counter: Int, val client_num: Int, val employee_num: Int,
    val wdate: LocalDateTime, val hours: Double, val start_time: LocalDateTime?,
    val end_time: LocalDateTime?, val daps_fee: Double?, val total_fee: Double?,
    val worktype: String?, val work_order_num: Int?, val open: Boolean?,
    val pmt1: String?, val apamt1: Double?, val pmt2: String?, val apamt2: Double?,
    val notesp: String?, val pending: Boolean?, val assigned_date: LocalDateTime?,
    val assigned_by: String?, val service_category: String?
) : Serializable {
    constructor(result_set: ResultSet) : this(
        result_set.getInt(BillingTable.counter.name),
        result_set.getInt(BillingTable.client_num.name),
        result_set.getInt(BillingTable.employee_num.name),
        LocalDateTime.parse(result_set.getString(BillingTable.wdate.name)),
        result_set.getDouble(BillingTable.hours.name),
        result_set.getString(BillingTable.start_time.name)?.let { LocalDateTime.parse(it)},
        result_set.getString(BillingTable.end_time.name)?.let { LocalDateTime.parse(it)},
        result_set.getDouble(BillingTable.daps_fee.name),
        result_set.getDouble(BillingTable.total_fee.name),
        result_set.getString(BillingTable.worktype.name),
        result_set.getInt(BillingTable.work_order_num.name),
        result_set.getBoolean(BillingTable.open.name),
        result_set.getString(BillingTable.pmt1.name),
        result_set.getDouble(BillingTable.apamt1.name),
        result_set.getString(BillingTable.pmt2.name),
        result_set.getDouble(BillingTable.apamt2.name),
        result_set.getString(BillingTable.notesp.name),
        result_set.getBoolean(BillingTable.pending.name),
        result_set.getString(BillingTable.assign_date.name)?.let { LocalDateTime.parse(it) },
        result_set.getString(BillingTable.assigned_by.name),
        result_set.getString(BillingTable.service_category.name)
    )
}
