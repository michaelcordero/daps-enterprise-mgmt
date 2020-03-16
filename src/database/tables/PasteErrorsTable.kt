package database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime

object PasteErrorsTable: Table() {
    val client_num = integer("Client#")
    val pmt_type = text("PmtType")
    val ref_num = text("Ref#")
    val pmt_date = datetime("PmtDate")
    val amount = decimal("Amount", 15, 15)
}
