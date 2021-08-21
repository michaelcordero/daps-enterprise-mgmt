package routes.web

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
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
