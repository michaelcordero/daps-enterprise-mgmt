package routes.web

import application.dq
import database.queries.DataQuery
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.util.KtorExperimentalAPI
import model.ClientFile
import presenters.WebClientsPresenter

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@Location("/webclients")
class WebClients

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.webclients(presenter: WebClientsPresenter){
    get<WebClients>{
        val clients: List<ClientFile> = dq.readClientFile(876) // emptyList()
        call.respond(FreeMarkerContent("clients.ftl", mapOf("clients" to clients, "presenter" to presenter), "clients-e-tag"))
    }
}
