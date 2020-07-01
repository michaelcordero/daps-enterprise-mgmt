package model.serializers

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.text.NumberFormat

class DoubleSerializer : JsonSerializer<Double>() {
    override fun serialize(value: Double?, gen: JsonGenerator?, serializers: SerializerProvider?) {
        val nf = NumberFormat.getNumberInstance()
        nf.minimumFractionDigits = 2
        gen?.writeObject(nf.format(value))
    }
}
