package routes.web

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import presenters.WebClientNotesPresenter
import java.time.LocalDateTime


@KtorExperimentalLocationsAPI
@Location("/webclientnotes")
class WebClientNotes


@KtorExperimentalLocationsAPI
fun Route.webclientnotes(presenter: WebClientNotesPresenter) {
    get<WebClientNotes> {
        call.respond(
            FreeMarkerContent(
                "client-notes.ftl",
                mapOf("presenter" to presenter),
                "client-notes-e-tag:${LocalDateTime.now()}"
            )
        )
    }
}
