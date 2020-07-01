package model.serializers

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider

class BooleanSerializer: JsonSerializer<Boolean>() {
    override fun serialize(value: Boolean?, gen: JsonGenerator?, serializers: SerializerProvider?) {
        val display: String
        value.let {
            if (value!!){
                display = "Yes"
            } else {
                display = "No"
            }
        }
        gen?.writeObject(display)
    }
}
