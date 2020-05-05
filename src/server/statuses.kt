package server

import io.ktor.application.call
import io.ktor.content.TextContent
import io.ktor.features.StatusPages
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.withCharset
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.response.respond
import io.ktor.response.respondText

// This extracts the 404 and 500 errors away from the application.kt file...
@KtorExperimentalLocationsAPI
fun statuses(configuration: StatusPages.Configuration ) {
    // 404 Error
    configuration.status(HttpStatusCode.NotFound) {
        call.respond(TextContent("${it.value} ${it.description}",
            ContentType.Text.Plain.withCharset(Charsets.UTF_8), it))
    }
    // 500 Error
    configuration.status(HttpStatusCode.InternalServerError){
        call.respondText { "${it.value} ${it.description}" }
    }
    // 401
    configuration.status(HttpStatusCode.Unauthorized) {
        call.respond(FreeMarkerContent("weblogin.ftl", mapOf("emailId" to "unknown", "error" to it.description), "someeetag"))
    }
}
