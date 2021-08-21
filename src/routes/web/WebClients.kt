package routes.web

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
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
