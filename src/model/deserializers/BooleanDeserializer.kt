package model.deserializers

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer

class BooleanDeserializer: JsonDeserializer<Boolean>() {
    override fun deserialize(jp: JsonParser?, ds: DeserializationContext?): Boolean {
        val truthy: String? = jp?.readValueAs(String::class.java)
        truthy.let {
            return when (truthy) {
                "Yes" -> true
                "No" -> false
                else -> throw IllegalArgumentException()
            }
        }
    }
}
