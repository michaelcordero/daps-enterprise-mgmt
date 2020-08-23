package routes.web

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import presenters.WebWorkOrderNotesPresenter
import java.time.LocalDateTime

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
@Location("/webworkordernotes")
class WebWorkOrderNotes

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
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
