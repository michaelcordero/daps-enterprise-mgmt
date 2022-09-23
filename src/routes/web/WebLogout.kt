package routes.web

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import presenters.WebLoginPresenter
import security.DAPSSession

@KtorExperimentalLocationsAPI
@Location("/weblogout")
class WebLogout


@KtorExperimentalLocationsAPI
fun Route.weblogout() {
    get<WebLogout> {
        val session = call.sessions.get<DAPSSession>()
        session?.sessionId = null
        call.sessions.clear<DAPSSession>()
        call.respond(FreeMarkerContent("weblogin.ftl", mapOf("presenter" to WebLoginPresenter()), "someetag"))
    }
}
