package server

import application.log
import io.ktor.http.*
import io.ktor.server.freemarker.*
import io.ktor.server.locations.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import presenters.WebLoginPresenter
import presenters.WebStatusPresenter

// This extracts the 404 and 500 errors away from the application.kt file...
@KtorExperimentalLocationsAPI
fun statuses(presenter: WebStatusPresenter) {
    // 404 Error
    presenter.configuration.status(HttpStatusCode.NotFound) {
        call, statusCode ->  call.respond(FreeMarkerContent("404.ftl", mapOf("user" to "null", "presenter" to presenter), "404-e-tag"))
    }
    // 500 Error
    presenter.configuration.status(HttpStatusCode.InternalServerError){
            call, statusCode -> call.respond(FreeMarkerContent("500.ftl", mapOf("user" to "null", "presenter" to presenter), "500-e-tag"))
    }
    // 401
    presenter.configuration.status(HttpStatusCode.Unauthorized) {
            call, statusCode ->
        // We need to know if this request originates from the Web or the API
        // || call.request.uri.contains("web/")
        if (call.request.uri.contains("api/")){
            log.info("URI: ${call.request.uri}")
            call.respondText { "${statusCode.value} ${statusCode.description}" }
        } else {
            log.info("URI: ${call.request.uri}")
            call.respond(FreeMarkerContent("weblogin.ftl", mapOf("error" to statusCode.description, "presenter" to WebLoginPresenter()), "401-e-tag"))
        }
    }
}
