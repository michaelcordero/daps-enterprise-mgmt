package routes.web

import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.util.KtorExperimentalAPI
import presenters.WebPermNotesPresenter
import java.time.LocalDateTime

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@Location("/webpermnotes")
class WebPermNotes

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
fun Route.webpermnotes(presenter: WebPermNotesPresenter) {
    get<WebPermNotes> {
        call.respond(FreeMarkerContent("perm-notes.ftl", mapOf("presenter" to presenter), "perm-notes-e-tag:${LocalDateTime.now()}"))
    }
}
