package model.deserializers

import application.log
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class LocalTimeDeserializer: JsonDeserializer<Timestamp>() {
    override fun deserialize(jp: JsonParser?, ctxt: DeserializationContext?): Timestamp? {
        val raw: String? = jp?.readValueAs(String::class.java)
        raw?.let {
            try{
                return if (it.isEmpty()) null
                else {
                    val dtf: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME
                    val lt: LocalTime
                    var text = it
                    if (text.substringBefore(":").length < 2) {
                        text = "0$it"
                    }
                    lt = when {
                        text.contains("AM") -> {
                            LocalTime.parse(text.substringBefore("AM").trim(),dtf)
                        }
                        text.contains("PM") -> {
                            LocalTime.parse(text.substringBefore("PM").trim(),dtf).plusHours(12)
                        }
                        else -> {
                            LocalTime.parse(text.trim(),dtf)
                        }
                    }
                    return Timestamp.valueOf(lt.atDate(LocalDate.now(ZoneId.systemDefault())))
                }
            } catch (e: Exception) {
                log.error(e.message)
            }
        }
        return null
    }
}
