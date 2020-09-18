package routes.web

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import presenters.WebClientsPresenter
import java.time.LocalDateTime

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@Location("/webclients")
class WebClients

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.webclients(presenter: WebClientsPresenter) {
    get<WebClients> {
            call.respond(
                FreeMarkerContent(
                    "clients.ftl",
                    mapOf("presenter" to presenter),
                    "clients-e-tag:${LocalDateTime.now()}"
                )
            )
    }
}
