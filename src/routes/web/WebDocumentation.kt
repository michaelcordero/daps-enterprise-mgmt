package routes.web

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
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
