package com.daps.ent.routes

import com.daps.ent.database.DataService
import com.daps.ent.model.User
import com.daps.ent.security.DAPSSession
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.routing.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.application.*
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.sessions.get
import io.ktor.sessions.sessions

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
        val user: User? = call.sessions.get<DAPSSession>()?.let { dao.user(it.userId) }
        if (user != null ) {
            call.respond(FreeMarkerContent("index.ftl", mapOf("" to ""), "some etag"))
        } else {
            call.respond(FreeMarkerContent("login.ftl", null, "some etag"))
        }

    }
}
