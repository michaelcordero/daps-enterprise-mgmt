package routes.web

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import presenters.WebTempsAvailableForWorkPresenter
import java.time.LocalDateTime

@KtorExperimentalLocationsAPI

@Location("/webtempsavailableforwork")
class WebTempsAvailableForWork


@KtorExperimentalLocationsAPI
fun Route.webtempsavailableforwork(presenter: WebTempsAvailableForWorkPresenter) {
    get<WebTempsAvailableForWork> {
        call.respond(
            FreeMarkerContent("temps-available-for-work.ftl",
        mapOf("presenter" to presenter),
        "web-temps-available-for-work-e-tag:${LocalDateTime.now()}")
        )
    }
}
