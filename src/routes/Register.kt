package com.daps.ent.routes

import com.daps.ent.model.User
import com.daps.ent.presenters.RegisterPresenter
import com.daps.ent.redirect
import com.daps.ent.security.DAPSSession
import io.ktor.application.application
import io.ktor.application.call
import io.ktor.application.log
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.Parameters
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalLocationsAPI
@Location("/register")
data class Register(
    val first_name: String = "",
    val last_name: String = "",
    val email: String = "",
    val error: String = ""
)

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.register(presenter: RegisterPresenter) {
    post<Register>{
        // get current user from session data if any
        val user: User? = call.sessions.get<DAPSSession>()?.let { presenter.user(it.emailId) }
        if (user != null ) return@post call.redirect(Welcome(it.email))

        // get post data
        val registration = call.receive<Parameters>()
        val first_name: String = registration["first_name"] ?: return@post call.redirect(it)
        val last_name: String = registration["last_name"] ?: return@post call.redirect(it)
        val email: String = registration["email"] ?: return@post call.redirect(it)
        val password: String = registration["password"] ?: return@post call.redirect(it)

        // do biz work
        try {
            presenter.createUser(first_name, last_name, email, password)
            call.sessions.set(DAPSSession(email))
            call.redirect(Welcome(email))
        } catch (e: Exception) {
            val error = Register(last_name, email, password)
            application.log.error("failed to register user", e)
            call.redirect(error.copy(error = e.cause.toString()))
        }
    }

    get<Register> {
        val user = call.sessions.get<DAPSSession>()?.let { presenter.user(it.emailId) }
        if (user != null ){
            call.respond(Welcome(it.email))
        } else {
            call.respond(FreeMarkerContent("register.ftl", mapOf("page_user" to User(
                it.email, it.first_name,
                it.last_name, ""
            ), "error" to it.error, "validator" to RegisterPresenter.Validator ), "some-etag"))
        }
    }
}

