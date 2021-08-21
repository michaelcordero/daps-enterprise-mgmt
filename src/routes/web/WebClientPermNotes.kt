package routes.web

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
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
