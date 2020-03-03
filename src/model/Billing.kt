package model

import org.joda.time.DateTime
import java.io.Serializable

data class Billing(val counter: Int, val client_num: Int, val employee_num: Int,
                   val wdate: DateTime, val hours: Double, val start_time: DateTime?,
                   val end_time: DateTime?, val daps_fee: Double?, val total_fee: Double?,
                   val worktype: String?, val work_order_num: Int?, val open: Boolean?,
                   val pmt1: String?, val apamt1: Double?, val pmt2: String?, val apamt2: Double?,
                   val notesp: String?, val pending: Boolean?, val assigned_date: DateTime?,
                   val assigned_by: String?, val service_category: String?
) : Serializable
