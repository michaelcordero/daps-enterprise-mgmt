package routes.web

import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.util.KtorExperimentalAPI
import presenters.WebDocumentationPresenter
import java.time.LocalDateTime

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@Location("/webdocumentation")
class WebDocumentation

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.webdocumentation(presenter: WebDocumentationPresenter){
    get<WebDocumentation> {
        call.respond(FreeMarkerContent("documentation.ftl", mapOf("presenter" to presenter), "documentation-e-tag:${LocalDateTime.now()}"))
    }
}
