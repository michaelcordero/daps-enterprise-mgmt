package model.deserializers

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer

class BooleanDeserializer: JsonDeserializer<Boolean>() {
    override fun deserialize(jp: JsonParser?, ds: DeserializationContext?): Boolean? {
        val truthy: String? = jp?.readValueAs(String::class.java)
        truthy.let {
            return when (truthy) {
                "Y","y","Yes", "yes", "YES", "YEs", "yeS","True","TRUE", "true" -> true
                "N","n","No", "no", "NO", "nO","False", "FALSE","false" -> false
                else -> null
            }
        }
    }
}
