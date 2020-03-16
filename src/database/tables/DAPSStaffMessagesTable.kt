package database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime

object DAPSStaffMessagesTable : Table(){
    val memo_date = datetime("Memo Date")
    val entered_by = text("Entered By")
    val intended_for = text("Intended For")
    val message = text("Message")
    val staff_messages_key = integer("StaffMessagesKey")
    override val primaryKey = PrimaryKey(staff_messages_key, name = "StaffMessagesKey")
}
