package com.daps.ent.routes

import com.daps.ent.model.User
import com.daps.ent.presenters.WelcomePresenter
import com.daps.ent.security.DAPSSession
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.sessions.get
import io.ktor.sessions.sessions

/*
Register the welcome route of the app
 */
@KtorExperimentalLocationsAPI
@Location("/welcome/{emailId}")
data class Welcome(val emailId: String)

// This uses the @Location feature to register the get route for '/welcome'.
@KtorExperimentalLocationsAPI
fun Route.welcome(presenter: WelcomePresenter) {
    get<Welcome> {
        // replace with db call obviously
        val user: User? = call.sessions.get<DAPSSession>()?.let { presenter.user(it.emailId) }
        if ( user == null ) {
            call.respond(HttpStatusCode.Unauthorized, "Unauthenticated Session Request")
        } else {
            call.respond(FreeMarkerContent("welcome.ftl", mapOf("user" to user ), "someetag"))
        }
    }
}
