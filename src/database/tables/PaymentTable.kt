package database.tables

import org.jetbrains.exposed.sql.Table

object PaymentTable: Table() {
    val client_num = integer("Client#").primaryKey()
    val pmt_type = text("PmtType").primaryKey()
    val ref_num = text("Ref#")
    val pmt_date = datetime("PmtDate")
    val amount = decimal("Amount", 15, 15)
}
