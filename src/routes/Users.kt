package com.daps.ent.routes

import com.daps.ent.facades.DataService
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import model.User

@KtorExperimentalLocationsAPI
@Location("/users")
class Users

@KtorExperimentalLocationsAPI
fun Route.users(dao: DataService){
    get<Users>{
        val users: List<User> = dao.all()
        call.respond(FreeMarkerContent("users.ftl", mapOf("users" to users), "someetag"))
    }
}
