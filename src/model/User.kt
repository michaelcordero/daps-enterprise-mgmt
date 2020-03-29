package model

import java.io.Serializable

data class User(
    val id: Long,
    val email: String,
    val first_name: String,
    val last_name: String,
    val passwordHash: String
) : Serializable
