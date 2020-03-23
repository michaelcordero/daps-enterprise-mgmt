package database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.timestamp

object PaymentTable: Table() {
    val client_num = integer("Client#")
    val pmt_type = text("PmtType").nullable()
    override val primaryKey = PrimaryKey(client_num, pmt_type)
    val ref_num = text("Ref#")
    val pmt_date = timestamp("PmtDate")
    val amount = double("Amount" )
}
