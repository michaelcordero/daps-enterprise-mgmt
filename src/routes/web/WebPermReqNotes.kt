package routes.web

import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.util.KtorExperimentalAPI
import presenters.WebPermReqNotesPresenter
import java.time.LocalDateTime

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@Location("/webpermreqnotes")
class WebPermReqNotes

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
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
