package com.daps.ent.routes

import com.daps.ent.model.User
import com.daps.ent.presenters.RegisterPresenter
import com.daps.ent.redirect
import com.daps.ent.security.DAPSSession
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.*
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.sessions.clear
import io.ktor.sessions.*
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalLocationsAPI
@Location("/login")
data class Login(val emailId: String = "", val error: String = "")

@KtorExperimentalLocationsAPI
@Location("/logout")
class Logout

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.login(presenter: RegisterPresenter ) {
    get<Login> {
        val user: User? = call.sessions.get<DAPSSession>()?.let { presenter.user(it.emailId) }
        if (user != null) {
            call.redirect(Welcome(user.email))
        } else {
            call.respond(FreeMarkerContent("login.ftl", mapOf("emailId" to it.emailId, "error" to it.error), "someetag"))
        }
    }

    post<Login> {
        val post: Parameters = call.receive()
        val password = post["password"] ?: return@post call.redirect(it)
        val emailId = post["emailId"] ?: return@post call.redirect(it)
        val error = Login(emailId, "")
        val user: User? = presenter.user(emailId)

        if (user == null) {
            call.redirect(error.copy(error = "Invalid email"))
        } else if (user.passwordHash != presenter.hashPassword(password)){
            call.redirect(error.copy(error = "Invalid password"))
        } else {
            call.sessions.set(DAPSSession(user.email))
            call.redirect(Welcome(user.email))
        }
    }

    get<Logout> {
        call.sessions.clear<DAPSSession>()
        call.redirect(Login())
    }
}

