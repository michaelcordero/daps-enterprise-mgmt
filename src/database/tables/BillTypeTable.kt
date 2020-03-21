package database.tables

import org.jetbrains.exposed.sql.Table

object BillTypeTable: Table() {
    val temp = text("Temp").nullable()
    val perm = text("Perm").nullable()
}
