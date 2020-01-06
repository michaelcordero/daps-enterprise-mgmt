package com.daps.ent.model

import java.io.Serializable

data class User (val userId: String, val email: String, val displayName: String, val passwordHash: String ) : Serializable
