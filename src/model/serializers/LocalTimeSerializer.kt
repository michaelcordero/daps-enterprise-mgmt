package model.serializers

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import io.ktor.util.InternalAPI
import java.sql.Timestamp
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class LocalTimeSerializer : JsonSerializer<Timestamp>() {
    @InternalAPI
    override fun serialize(value: Timestamp?, gen: JsonGenerator?, serializers: SerializerProvider?) {
        gen?.writeObject(value?.toLocalDateTime()?.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)))
    }
}
