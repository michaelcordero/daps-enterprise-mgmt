package database.tables

import org.jetbrains.exposed.sql.Table

object DAPSAddressTable: Table() {
    val mailinglist_id = integer("MailingListID").primaryKey()
    val office = text("Office")
    val address1 = text("Address1")
    val address2 = text("Address2")
    val city = text("City")
    val state = text("State")
    val zipcode = text("Zip")
}
