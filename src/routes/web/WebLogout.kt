package routes.web

import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.sessions.clear
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import presenters.WebLoginPresenter
import security.DAPSSession

@KtorExperimentalLocationsAPI
@Location("/weblogout")
class WebLogout


@KtorExperimentalLocationsAPI
fun Route.weblogout() {
    get<WebLogout> {
        val session = call.sessions.get<DAPSSession>()
        session?.token = null
        call.sessions.clear<DAPSSession>()
        call.respond(FreeMarkerContent("weblogin.ftl", mapOf("presenter" to WebLoginPresenter()), "someetag"))
    }
}
