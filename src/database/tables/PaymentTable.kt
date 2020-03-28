package database.tables

import database.realtimestamp
import org.jetbrains.exposed.sql.Table

object PaymentTable: Table() {
    val client_num = integer("Client#")
    val pmt_type = text("PmtType")
    val ref_num = text("Ref#").autoIncrement()
    override val primaryKey = PrimaryKey(client_num, ref_num)
    val pmt_date = realtimestamp("PmtDate")
    val amount = double("Amount" )
}
