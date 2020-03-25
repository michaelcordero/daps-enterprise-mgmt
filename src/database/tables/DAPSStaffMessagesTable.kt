package database.tables

import database.realtimestamp
import org.jetbrains.exposed.sql.Table

object DAPSStaffMessagesTable : Table(){
    val memo_date = realtimestamp("Memo Date").nullable()
    val entered_by = text("Entered By").nullable()
    val intended_for = text("Intended For").nullable()
    val message = text("Message").nullable()
    val staff_messages_key = integer("StaffMessagesKey")
    override val primaryKey = PrimaryKey(staff_messages_key, name = "StaffMessagesKey")
}
