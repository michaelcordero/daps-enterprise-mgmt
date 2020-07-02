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
import presenters.WebClientsPresenter
import security.DAPSSession

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@Location("/webclients")
class WebClients

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.webclients(presenter: WebClientsPresenter) {
    get<WebClients> {
        val session: DAPSSession? = call.sessions.get<DAPSSession>()
        if (session != null) {
            call.respond(
                FreeMarkerContent(
                    "clients.ftl",
                    mapOf("presenter" to presenter),
                    "clients-e-tag"
                )
            )
        } else {
            call.respond(FreeMarkerContent("weblogin.ftl", mapOf("user" to "null"), "webclient-e-tag"))
        }
    }
}
