package database.tables

import org.jetbrains.exposed.sql.Table

object AccountRepDropDownTable :Table() {
    val account_rep = text("AccountRep")
}
