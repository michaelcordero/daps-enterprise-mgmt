package routes.web

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import presenters.WebTempsPresenter
import java.time.LocalDateTime

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@Location("/webtemps")
class WebTemps

@KtorExperimentalAPI
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
