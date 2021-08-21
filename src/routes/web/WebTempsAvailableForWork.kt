package routes.web

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import presenters.WebTempsAvailableForWorkPresenter
import java.time.LocalDateTime

@KtorExperimentalLocationsAPI

@Location("/webtempsavailableforwork")
class WebTempsAvailableForWork


@KtorExperimentalLocationsAPI
fun Route.webtempsavailableforwork(presenter: WebTempsAvailableForWorkPresenter) {
    get<WebTempsAvailableForWork> {
        call.respond(FreeMarkerContent("temps-available-for-work.ftl",
        mapOf("presenter" to presenter),
        "web-temps-available-for-work-e-tag:${LocalDateTime.now()}"))
    }
}
