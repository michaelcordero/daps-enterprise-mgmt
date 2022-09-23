package routes.web

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import presenters.WebWorkOrderNotesPresenter
import java.time.LocalDateTime


@KtorExperimentalLocationsAPI
@Location("/webworkordernotes")
class WebWorkOrderNotes

@KtorExperimentalLocationsAPI

fun Route.webworkordernotes(presenter: WebWorkOrderNotesPresenter) {
    get<WebWorkOrderNotes> {
        call.respond(
            FreeMarkerContent(
                "work-order-notes.ftl",
                mapOf("presenter" to presenter),
                "work-order-notes-e-tag:${LocalDateTime.now()}"
            )
        )
    }
}
