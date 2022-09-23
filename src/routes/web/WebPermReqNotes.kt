package routes.web

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
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
