package model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import database.tables.BillingTable
import model.serializers.BooleanSerializer
import model.serializers.DoubleSerializer
import model.serializers.LocalDateSerializer
import model.serializers.LocalTimeSerializer
import java.io.Serializable
import java.sql.ResultSet
import java.sql.Timestamp

data class Billing @JsonCreator constructor(
    @JsonProperty(value = "counter", required = true)
    val counter: Int,
    @JsonProperty(value = "client_num", required = true)
    val client_num: Int,
    @JsonProperty(value = "employee_num", required = true)
    val employee_num: Int,
    @JsonSerialize(using = LocalDateSerializer::class)
    val wdate: Timestamp?,
    @JsonSerialize(using = DoubleSerializer::class)
    val hours: Double,
    @JsonSerialize(using = LocalTimeSerializer::class)
    val start_time: Timestamp?,
    @JsonSerialize(using = LocalTimeSerializer::class)
    val end_time: Timestamp?,
    @JsonSerialize(using = DoubleSerializer::class)
    val daps_fee: Double?,
    @JsonSerialize(using = DoubleSerializer::class)
    val total_fee: Double?,
    val worktype: String?,
    val work_order_num: Int?,
    @JsonSerialize(using = BooleanSerializer::class)
    val open: Boolean?,
    val pmt1: String?,
    @JsonSerialize(using = DoubleSerializer::class)
    val apamt1: Double?,
    val pmt2: String?,
    @JsonSerialize(using = DoubleSerializer::class)
    val apamt2: Double?,
    val notesp: String?,
    @JsonSerialize(using = BooleanSerializer::class)
    val pending: Boolean?,
    @JsonSerialize(using = LocalDateSerializer::class)
    val assigned_date: Timestamp?,
    val assigned_by: String?,
    val service_category: String?
) : Serializable {
    constructor(result_set: ResultSet) : this(
        result_set.getInt(BillingTable.counter.name),
        result_set.getInt(BillingTable.client_num.name),
        result_set.getInt(BillingTable.employee_num.name),
        result_set.getString(BillingTable.wdate.name)?.let { Timestamp.valueOf(it) },
        result_set.getDouble(BillingTable.hours.name),
        result_set.getString(BillingTable.start_time.name)?.let { Timestamp.valueOf(it) },
        result_set.getString(BillingTable.end_time.name)?.let { Timestamp.valueOf(it) },
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
        result_set.getString(BillingTable.assign_date.name)?.let { Timestamp.valueOf(it) },
        result_set.getString(BillingTable.assigned_by.name),
        result_set.getString(BillingTable.service_category.name)
    )
}
