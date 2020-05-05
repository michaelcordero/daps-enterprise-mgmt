package database.tables

import database.realtimestamp
import org.jetbrains.exposed.sql.Table

object PasteErrorsTable: Table() {
    val client_num = integer("Client#")
    val pmt_type = text("PmtType").nullable()
    val ref_num = text("Ref#")
    override val primaryKey = PrimaryKey(ref_num, name = "Ref#")
    val pmt_date = realtimestamp("PmtDate").nullable()
    val amount = double("Amount").nullable()
}
