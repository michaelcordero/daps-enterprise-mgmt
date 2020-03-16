package database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime

object PaymentTable: Table() {
    val client_num = integer("Client#")
    val pmt_type = text("PmtType")
    override val primaryKey = PrimaryKey(client_num, pmt_type)
    val ref_num = text("Ref#")
    val pmt_date = datetime("PmtDate")
    val amount = decimal("Amount", 15, 15)
}
