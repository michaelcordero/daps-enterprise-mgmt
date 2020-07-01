package model.deserializers

import application.log
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import io.ktor.util.InternalAPI
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class LocalDateDeserializer: JsonDeserializer<Timestamp>() {
    @InternalAPI
    override fun deserialize(jp: JsonParser?, ds: DeserializationContext?): Timestamp? {
        val raw: String? = jp?.readValueAs(String::class.java)
        raw?.let {
            try {
                return if (raw.isEmpty()) {
                    null
                } else {
                    val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.US)
                    val date: Date = sdf.parse(raw)
                    Timestamp(date.time)
                }
            } catch (e: Exception) {
                log.error(e.message)
            }
        }
        return null
    }

}
