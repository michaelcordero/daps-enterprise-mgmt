package database.tables

import org.jetbrains.exposed.sql.Table

object BillTypeDropDownTable: Table() {
    val temp = text("Temp")
    val perm = text("Perm")
}
