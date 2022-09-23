package routes.web

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import presenters.WebClientPermNotesPresenter
import java.time.LocalDateTime


@KtorExperimentalLocationsAPI
@Location("/webclientpermnotes")
class WebClientPermNotes


@KtorExperimentalLocationsAPI
fun Route.webclientpermnotes(presenter: WebClientPermNotesPresenter) {
    get<WebClientPermNotes> {
        call.respond(
            FreeMarkerContent(
                "client-perm-notes.ftl",
                mapOf("presenter" to presenter),
                "client-perm-notes-e-tag:${LocalDateTime.now()}"
            )
        )
    }
}
