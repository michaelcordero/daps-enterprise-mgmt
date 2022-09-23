package routes.web

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import presenters.WebPermReqNotesPresenter
import java.time.LocalDateTime

@KtorExperimentalLocationsAPI

@Location("/webpermreqnotes")
class WebPermReqNotes

@KtorExperimentalLocationsAPI

fun Route.webpermreqnotes(presenter: WebPermReqNotesPresenter) {
    get<WebPermReqNotes> {
        call.respond(
            FreeMarkerContent(
                "perm-req-notes.ftl",
                mapOf("presenter" to presenter),
                "perm-req-notes-e-tag:${LocalDateTime.now()}"
            )
        )
    }
}
