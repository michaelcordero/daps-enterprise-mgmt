package database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime

object DAPSStaffMessagesTable : Table(){
    val memo_date = datetime("Memo Date").nullable()
    val entered_by = text("Entered By").nullable()
    val intended_for = text("Intended For").nullable()
    val message = text("Message").nullable()
    val staff_messages_key = integer("StaffMessagesKey")
    override val primaryKey = PrimaryKey(staff_messages_key, name = "StaffMessagesKey")
}
