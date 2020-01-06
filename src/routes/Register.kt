package com.daps.ent.routes

import com.daps.ent.database.DataService
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.response.respond
import io.ktor.routing.Route

@KtorExperimentalLocationsAPI
@Location("/register")
class Register

@KtorExperimentalLocationsAPI
fun Route.register(dao: DataService) {
    post<Register>{

    }

    get<Register> {
        call.respond(FreeMarkerContent("register.ftl", null, "some-etag"))
    }
}

