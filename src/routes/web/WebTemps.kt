package routes.web

import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.util.KtorExperimentalAPI
import presenters.WebTempsPresenter

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@Location("/webtemps")
class WebTemps

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.webtemps(presenter: WebTempsPresenter) {
    get<WebTemps> {
        call.respond(FreeMarkerContent("temps.ftl", mapOf("presenter" to presenter), "web-temps-tag"))
    }
}
