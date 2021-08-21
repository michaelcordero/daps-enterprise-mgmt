package routes.web

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
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
