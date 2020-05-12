package routes.web

import application.dq
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
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import model.ClientFile
import presenters.WebClientsPresenter
import security.DAPSSession

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@Location("/webclients")
class WebClients

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.webclients(presenter: WebClientsPresenter){
    get<WebClients>{
        val session: DAPSSession? = call.sessions.get<DAPSSession>()
        if (session != null) {
            val clients: Deferred<List<ClientFile>> = async {  return@async dq.allClientFiles() } // sad :/
            call.respond(FreeMarkerContent("clients.ftl", mapOf("clients" to clients.await(),
                "presenter" to presenter), "clients-e-tag")) //"user" to user,
        } else {
            call.respond(FreeMarkerContent("weblogin.ftl", mapOf("user" to "null"), "webclient-e-tag"))
        }
    }
}
