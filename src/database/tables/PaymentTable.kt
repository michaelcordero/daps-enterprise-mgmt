package database.tables

import database.realtimestamp
import org.jetbrains.exposed.sql.Table

object PaymentTable: Table() {
    val client_num = integer("Client#").nullable()
    val pmt_type = text("PmtType").nullable()
    val ref_num = varchar("Ref#", 20).nullable()
    override val primaryKey = PrimaryKey(client_num, ref_num)
    val pmt_date = realtimestamp("PmtDate").nullable()
    val amount = double("Amount" ).nullable()
}
