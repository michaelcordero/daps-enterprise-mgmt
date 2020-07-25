package routes.web

import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.util.KtorExperimentalAPI
import presenters.WebClientNotesPresenter
import presenters.WebLoginPresenter
import security.DAPSSession

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
@Location("/webclientnotes")
class WebClientNotes

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.webclientnotes(presenter: WebClientNotesPresenter) {
    get<WebClientNotes> {
        val session: DAPSSession? = call.sessions.get<DAPSSession>()
        if (session != null) {
            call.respond(
                FreeMarkerContent(
                    "client-notes.ftl",
                    mapOf("presenter" to presenter),
                    "client-notes-e-tag"
                )
            )
        } else {
            call.respond(FreeMarkerContent("weblogin.ftl", mapOf("presenter" to WebLoginPresenter()), "webclient-notes-e-tag"))
        }
    }
}
