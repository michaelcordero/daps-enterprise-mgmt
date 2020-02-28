package com.daps.ent.validators

interface RegisterValidator {
    fun validFirstName(first_name: String): Boolean
    fun validLastName(last_name: String): Boolean
    fun validEmail(email: String): Boolean
    fun validPassword(password: String): Boolean
}
