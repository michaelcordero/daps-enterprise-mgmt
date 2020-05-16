package routes.api

import application.log
import cache.InMemoryCache
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.util.KtorExperimentalAPI
import model.ClientFile

@KtorExperimentalLocationsAPI
@Location("/clients")
class Clients {
    @Location("/{client_num}")
    data class Client(val client_num: String, val clients: Clients)
}

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.clients(cache: InMemoryCache) {
    get<Clients> {
        try {
            val clients: List<ClientFile> = cache.clientFiles
            log.info("/clients requested")
            call.respond(status = HttpStatusCode.OK, message = clients)
        } catch (e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = "Bad Request: ${e}")
        }
    }

    get<Clients.Client> {
        try {
            val client: ClientFile? = cache.clientFiles.find { cf -> cf.client_num.equals(it.client_num.toInt()) }
            call.respond(mapOf("client" to client))
        } catch (e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = "Bad Request: ${e}")
        }
    }

//    post<Clients> {
//        try {
//            val clientFile: ClientFile = call.receive()
//            val result: Int = cache.createClientFile(clientFile)
//            call.respond(status = HttpStatusCode.OK, message = mapOf("added client" to true, "client_num" to result))
//        } catch (e: Exception) {
//            call.respond(status = HttpStatusCode.BadRequest, message =  e.toString())
//        }
//    }
//
//    put<Clients.Client> {
//        try {
//            val clientFile: ClientFile = call.receive()
//            val result: Int = dq.updateClientFile(clientFile)
//            call.respond(status = HttpStatusCode.OK, message = mapOf("updated client" to true, "result" to result))
//        } catch (e: Exception) {
//            call.respond(status = HttpStatusCode.BadRequest, message =  e.toString())
//        }
//    }
//
//    delete<Clients.Client> {
//        try {
//            val result: Int = cache.deleteClientFile(it.client_num.toInt())
//            call.respond(status = HttpStatusCode.OK, message = mapOf("deleted client" to true, "result" to result))
//        } catch (e: Exception) {
//            call.respond(status = HttpStatusCode.BadRequest, message =  e.toString())
//        }
//    }
}
