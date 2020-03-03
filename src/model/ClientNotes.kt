package model

import org.joda.time.DateTime

data class ClientNotes(val client_num: Int, val notedate: DateTime?,
                       val initial: String?, val note: String?,
                       val client_note_key: Int?)
