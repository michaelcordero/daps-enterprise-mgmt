package server

import application.log
import io.ktor.application.call
import io.ktor.features.StatusPages
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.request.uri
import io.ktor.response.respond
import io.ktor.response.respondText

// This extracts the 404 and 500 errors away from the application.kt file...
@KtorExperimentalLocationsAPI
fun statuses(configuration: StatusPages.Configuration ) {
    // 404 Error
    configuration.status(HttpStatusCode.NotFound) {
        call.respond(FreeMarkerContent("404.ftl", mapOf("user" to "null"), "404-e-tag"))
    }
    // 500 Error
    configuration.status(HttpStatusCode.InternalServerError){
        call.respond(FreeMarkerContent("500.ftl", mapOf("user" to "null"), "500-e-tag"))
    }
    // 401
    configuration.status(HttpStatusCode.Unauthorized) {
        // We need to know if this request originates from the Web or the API
        // || call.request.uri.contains("web/")
        if (call.request.uri.contains("api/")){
            log.info("URI: ${call.request.uri}")
            call.respondText { "${it.value} ${it.description}" }
        } else {
            log.info("URI: ${call.request.uri}")
            call.respond(FreeMarkerContent("weblogin.ftl", mapOf("error" to it.description), "401-e-tag"))
        }
    }
}
