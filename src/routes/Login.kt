package com.daps.ent.routes

import com.daps.ent.database.DataService
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route

@KtorExperimentalLocationsAPI
@Location("/login")
class Login

@KtorExperimentalLocationsAPI
fun Route.login(dao: DataService) {
    get<Login> {
        call.respond(FreeMarkerContent("login.ftl", null, "someetag"))
    }
}
