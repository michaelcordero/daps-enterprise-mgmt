package model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class User
@JsonCreator constructor(
    @JsonProperty(value = "id", required = true)
    val id: Long,
    @JsonProperty(value = "email", required = true)
    val email: String,
    @JsonProperty(value = "first_name", required = true)
    val first_name: String,
    @JsonProperty(value = "last_name", required = true)
    val last_name: String,
    @JsonProperty(value = "passwordHash", required = true)
    val passwordHash: String
) : Serializable
