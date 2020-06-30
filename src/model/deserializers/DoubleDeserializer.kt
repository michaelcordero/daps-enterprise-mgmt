package model.deserializers

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer

class DoubleDeserializer: JsonDeserializer<Double>() {
    override fun deserialize(jp: JsonParser?, ctxt: DeserializationContext?): Double? {
        val value: String? = jp?.readValueAs(String::class.java)
        return value?.toDoubleOrNull()
    }
}
