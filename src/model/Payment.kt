package model

import java.time.LocalDateTime

data class Payment(val client_num: Int?, val pmt_type: String?,
                   val ref_num: String?, val pmt_date: LocalDateTime?,
                   val amount: Double?)
