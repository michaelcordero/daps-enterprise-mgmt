package com.daps.ent.routes

import com.daps.ent.database.DataService
import com.daps.ent.model.User
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route

/*
Register the welcome route of the app
 */
@KtorExperimentalLocationsAPI
@Location("/welcome")
data class Welcome(val name: String = "")

// This uses the @Location feature to register the get route for '/welcome'.
@KtorExperimentalLocationsAPI
fun Route.welcome(dao: DataService) {
    get<Welcome> {
        // replace with db call obviously
        val user: User? =
         User(
             userId = "test", email = "user@email.com", first_name = "test",
             last_name ="user",
             passwordHash = "6819b57a326945c1968f45236589"
         )
        if ( user == null ) {
            call.respond(HttpStatusCode.InternalServerError, "missing name paramter")
        } else {
            call.respond(FreeMarkerContent("welcome.ftl", mapOf("user" to user ), "someetag"))
        }
    }
}
