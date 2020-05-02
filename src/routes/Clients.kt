package routes

import database.queries.DataQuery
import io.ktor.application.application
import io.ktor.application.call
import io.ktor.application.log
import io.ktor.features.ContentTransformationException
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.util.KtorExperimentalAPI
import model.ClientFile
import java.util.*

@KtorExperimentalLocationsAPI
@Location("/clients")
class Clients {
    @Location("/{client_num}")
    data class Client(val client_num: String, val clients: Clients)
}

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.clients(dao: DataQuery) {
    get<Clients> {
        try {
            val clients: List<ClientFile> = Collections.synchronizedList(dao.allClientFiles())
            call.respond(mapOf("clients" to synchronized(clients) { clients.toList()} ))
        } catch (e: Exception) {
            application.log.error(e.toString())
        }
    }

    get<Clients.Client> {
        try {
            val client: List<ClientFile> = Collections.synchronizedList(dao.readClientFile(it.client_num.toInt()))
            call.respond(mapOf("clients.read" to synchronized(client) {client.toList()}))
        } catch (e: Exception) {
            application.log.error(e.cause.toString())
        }
    }

    post<Clients> {
        try {
            val clientFile: ClientFile = call.receive()
            val result: Int = dao.createClientFile(clientFile)
            call.respond(status = HttpStatusCode.OK, message = mapOf("success!" to true, "client_num" to result))
        } catch (e: ContentTransformationException) {
            call.respond(status = HttpStatusCode.UnsupportedMediaType, message = "invalid JSON object")
        } catch (e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message =  "invalid JSON object")
        }
    }
}
