package com.daps.ent.model

import java.io.Serializable

data class User(
    val email: String,
    val first_name: String,
    val last_name: String,
    val passwordHash: String
) : Serializable
