package model

import java.time.LocalDateTime

data class ClientNotes(val client_num: Int, val notedate: LocalDateTime?,
                       val initial: String?, val note: String?,
                       val client_note_key: Int?)
