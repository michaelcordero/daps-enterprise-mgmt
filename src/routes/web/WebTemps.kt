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
import security.DAPSSession

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@Location("/webtemps")
class WebTemps

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.webtemps() {
get<WebTemps> {
    val session: DAPSSession? = call.sessions.get<DAPSSession>()
    if (session != null) {
        call.respond(FreeMarkerContent("temps.ftl", null, "web-temps-tag"))
    } else {
        call.respond(FreeMarkerContent("weblogin.ftl", mapOf("user" to "null"), "webclient-e-tag"))
    }
}
}
