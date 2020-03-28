package database.tables

import database.realtimestamp
import org.jetbrains.exposed.sql.Table

object PasteErrorsTable: Table() {
    val client_num = integer("Client#").nullable()
    val pmt_type = text("PmtType").nullable()
    val ref_num = text("Ref#").nullable()
    val pmt_date = realtimestamp("PmtDate").nullable()
    val amount = double("Amount").nullable()
}
