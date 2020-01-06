package com.daps.ent.routes

import com.daps.ent.database.DataService
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.routing.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.application.*
import io.ktor.freemarker.FreeMarkerContent

@KtorExperimentalLocationsAPI
@Location("/")
class Index

/*
Register the index route of the app
 */
@KtorExperimentalLocationsAPI
fun Route.index(dao: DataService){
    // This uses the @Location feature to register the get route for '/'.
    get<Index> {
        // call.respondText("Hello Ktor!", contentType = ContentType.Text.Plain)
        //call.respondHtml { body { +"Hello Ktor!" } }
        call.respond(FreeMarkerContent("index.ftl", mapOf("" to ""), "some etag"))
    }
}
