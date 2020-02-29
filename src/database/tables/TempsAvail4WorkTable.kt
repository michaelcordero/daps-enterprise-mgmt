package database.tables

import org.jetbrains.exposed.sql.Table

object TempsAvail4WorkTable: Table() {
    val rec_num = integer("RecNumber")
    val emp_num = integer("Emp#")
    val date_can_work = datetime("DateCanWork")
}
