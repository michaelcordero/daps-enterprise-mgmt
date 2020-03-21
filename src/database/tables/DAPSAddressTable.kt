package database.tables

import org.jetbrains.exposed.sql.Table

object DAPSAddressTable: Table() {
    val mailinglist_id = integer("MailingListID")
    override val primaryKey = PrimaryKey(mailinglist_id, name = "MailingListID")
    val office = text("Office").nullable()
    val address1 = text("Address1").nullable()
    val address2 = text("Address2").nullable()
    val city = text("City").nullable()
    val state = text("ST").nullable()
    val zipcode = text("Zip").nullable()
}
