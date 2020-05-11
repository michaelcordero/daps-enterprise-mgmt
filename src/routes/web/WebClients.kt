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
//            val user: User = dq.userByEmail(session.emailId)!!
//            val client_future: CompletableFuture<List<ClientFile>> = CompletableFuture.supplyAsync { dq.allClientFiles() }
            val clients: List<ClientFile> = dq.allClientFiles()
//            val job = CoroutineScope(Dispatchers.IO).future {
//                clients = dq.allClientFiles()// dq.readClientFile(876)  // emptyList() // dq.allClientFiles()
//            }
            call.respond(FreeMarkerContent("clients.ftl", mapOf("clients" to clients, "presenter" to presenter), "clients-e-tag")) //"user" to user,
        } else {
            call.respond(FreeMarkerContent("weblogin.ftl", mapOf("user" to "null"), "webclient-e-tag"))
        }
    }
}
