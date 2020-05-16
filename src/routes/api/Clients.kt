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
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

@KtorExperimentalLocationsAPI
@Location("/clients")
class Clients {
    @Location("/{client_num}")
    data class Client(val client_num: String, val clients: Clients)
}

@ExperimentalTime
@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.clients(cache: InMemoryCache) {
    get<Clients> {
        try {
            val clients: List<ClientFile> = cache.clientFiles
            log.info("/clients requested")
            val time: TimedValue<Unit> = measureTimedValue {
                call.respond(status = HttpStatusCode.OK, message = clients)
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = "Bad Request: ${e}")
        }
    }

    get<Clients.Client> {
        try {
            log.info("/clients/{client_num} requested")
            val time: TimedValue<Unit> = measureTimedValue {
                val client: ClientFile? = cache.clientFiles.find { cf -> cf.client_num.equals(it.client_num.toInt()) }
                call.respond(mapOf("client" to client))
            }
            log.info("Response took: ${time.duration}")
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
