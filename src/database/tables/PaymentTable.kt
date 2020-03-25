package database.tables

import database.realtimestamp
import org.jetbrains.exposed.sql.Table

object PaymentTable: Table() {
    val client_num = integer("Client#")
    val pmt_type = text("PmtType").nullable()
    override val primaryKey = PrimaryKey(client_num, pmt_type)
    val ref_num = text("Ref#")
    val pmt_date = realtimestamp("PmtDate")
    val amount = double("Amount" )
}
