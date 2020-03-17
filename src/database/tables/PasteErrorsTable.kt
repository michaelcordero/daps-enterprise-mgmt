package database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime

object PasteErrorsTable: Table() {
    val client_num = integer("Client#").nullable()
    val pmt_type = text("PmtType").nullable()
    val ref_num = text("Ref#").nullable()
    val pmt_date = datetime("PmtDate").nullable()
    val amount = double("Amount")
}
