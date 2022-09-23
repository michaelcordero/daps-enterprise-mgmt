package routes.web

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import presenters.WebDocumentationPresenter
import java.time.LocalDateTime

@KtorExperimentalLocationsAPI

@Location("/webdocumentation")
class WebDocumentation


@KtorExperimentalLocationsAPI
fun Route.webdocumentation(presenter: WebDocumentationPresenter){
    get<WebDocumentation> {
        call.respond(FreeMarkerContent("documentation.ftl", mapOf("presenter" to presenter), "documentation-e-tag:${LocalDateTime.now()}"))
    }
}
