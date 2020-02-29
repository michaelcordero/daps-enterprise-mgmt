package com.daps.ent.presenters

import com.daps.ent.Application.dao
import com.daps.ent.facades.DataService
import com.daps.ent.security.DAPSSecurity
import io.ktor.util.KtorExperimentalAPI
import model.User

abstract class AbstractPresenter(dao: DataService) {
    fun user(email: String): User? {
        return dao.userByEmail(email)
    }

    @KtorExperimentalAPI
    fun hashPassword(password: String): String {
        return DAPSSecurity.hash(password)
    }

}
