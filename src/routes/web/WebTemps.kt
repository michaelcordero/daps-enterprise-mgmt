package routes.web

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import presenters.WebTempsPresenter
import java.time.LocalDateTime

@KtorExperimentalLocationsAPI

@Location("/webtemps")
class WebTemps


@KtorExperimentalLocationsAPI
fun Route.webtemps(presenter: WebTempsPresenter) {
    get<WebTemps> {
        call.respond(
            FreeMarkerContent(
                "temps.ftl",
                mapOf("presenter" to presenter),
                "web-temps-e-tag:${LocalDateTime.now()}"
            )
        )
    }
}
