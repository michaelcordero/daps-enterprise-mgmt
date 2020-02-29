package database.tables

import org.jetbrains.exposed.sql.Table

object DAPSStaffMessagesTable : Table(){
    val memo_date = datetime("Memo Date")
    val entered_by = text("Entered By")
    val intended_for = text("Intended For")
    val message = text("Message")
    val staff_messages_key = integer("StaffMessagesKey").primaryKey()
}
