package com.daps.ent.routes

import com.daps.ent.Application.redirect
import com.daps.ent.facades.DataService
import com.daps.ent.security.DAPSSession
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import model.User

@KtorExperimentalLocationsAPI
@Location("/")
class Index

/*
Register the index route of the app
 */
@KtorExperimentalLocationsAPI
fun Route.index(dao: DataService){
    // If the user has not already been authenticated we want to redirect to the login page otherwise dashboard.
    get<Index> {
        val user: User? = call.sessions.get<DAPSSession>()?.let { dao.user(it.emailId) }
        if (user != null ) {
            // this probably needs
            call.redirect(location = Welcome(user.email))
//            call.respond(FreeMarkerContent("welcome.ftl", mapOf("user" to user), "someetag"))
        } else {
            call.respond(FreeMarkerContent("login.ftl", null, "some-etag"))
        }

    }
}
