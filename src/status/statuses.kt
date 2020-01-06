package com.daps.ent.status

import io.ktor.application.call
import io.ktor.content.TextContent
import io.ktor.features.StatusPages
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.withCharset
import io.ktor.response.respond
import io.ktor.response.respondText

// This extracts the 404 and 500 errors away from the Application.kt file...
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
    //val status_pages: StatusPages = StatusPages(configuration)
}
