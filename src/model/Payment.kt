package model

import org.joda.time.DateTime

data class Payment(val client_num: Int?, val pmt_type: String?,
                   val ref_num: String?, val pmt_date: DateTime?,
                   val amount: Double?)
