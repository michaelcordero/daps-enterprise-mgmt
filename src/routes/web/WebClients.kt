package routes.web

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import presenters.WebClientsPresenter

@KtorExperimentalLocationsAPI

@Location("/webclients")
class WebClients


@KtorExperimentalLocationsAPI
fun Route.webclients(presenter: WebClientsPresenter) {
    get<WebClients> {
            call.respond(
                FreeMarkerContent(
                    "clients.ftl",
                    mapOf("presenter" to presenter),
                    "clients-e-tag"
                )
            )
    }
}
