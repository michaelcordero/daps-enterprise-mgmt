package com.daps.ent.presenters

import com.daps.ent.dao
import com.daps.ent.database.DataService
import com.daps.ent.model.User
import com.daps.ent.security.DAPSSecurity
import io.ktor.util.KtorExperimentalAPI

abstract class AbstractPresenter(dao: DataService) {
    fun user(email: String): User? {
        return dao.userByEmail(email)
    }

    @KtorExperimentalAPI
    fun hashPassword(password: String): String {
        return DAPSSecurity.hash(password)
    }

    companion object AbstractValidator{
        val password_pattern: Regex = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%]).{6,20})".toRegex()
        val email_pattern: Regex = ("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*+(\\+[_A-Za-z0-9-]+)*@[A-Za-z0-9.-]" +
                "+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})\$").toRegex()
        fun validEmail(email: String): Boolean {
            return email.matches(email_pattern)
        }
        fun validPassword(password: String): Boolean {
            return password.matches(password_pattern)
        }
    }
}
