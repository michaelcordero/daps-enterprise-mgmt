package database.tables

import database.realtimestamp
import org.jetbrains.exposed.sql.Table

object TempsAvail4WorkTable: Table() {
    val rec_num = integer("RecNumber").nullable()
    val emp_num = integer("Emp#").nullable()
    val date_can_work = realtimestamp("DateCanWork")
}
