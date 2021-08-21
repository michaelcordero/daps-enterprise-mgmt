package routes.web

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
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
