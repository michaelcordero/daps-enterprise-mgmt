package routes.api

import application.log
import cache.DataCache
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
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
fun Route.clients(cache: DataCache) {
    get<Clients> {
        try {
            log.info("GET /clients requested")
            val time: TimedValue<Unit> = measureTimedValue {
                call.respond(status = HttpStatusCode.OK, message = cache.allClientFiles())
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = "Bad Request: $e")
        }
    }

    get<Clients.Client> {
        try {
            log.info("GET /clients/{client_num} requested")
            val time: TimedValue<Unit> = measureTimedValue {
                val client: ClientFile? = cache.allClientFiles().find { cf -> cf.client_num == it.client_num.toInt() }
                call.respond(mapOf("client" to client))
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = "Bad Request: $e")
        }
    }

    post<Clients> {
        try {
            log.info("POST /clients requested")
            val clientFile: ClientFile = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val result: Int = cache.add(clientFile)
                val cfr = cache.allClientFiles().find { cf -> cf.client_num == result }
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(cfr)))
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message =  e.toString())
        }
    }

    put<Clients> {
        try {
            log.info("PUT /clients requested")
            val clientFile: ClientFile = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val result: Int = cache.edit(clientFile)
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(clientFile), "result" to result))
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = e.toString())
        }
    }

    delete<Clients> {
        try {
            log.info("DELETE /clients requested")
            val clientFile: ClientFile = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val cf: ClientFile? = cache.allClientFiles().find { c -> c.client_num == clientFile.client_num }
                val result: Int = cf.let { cache.remove(cf) }
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to emptyList<ClientFile>(), "result" to result))
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = e.toString())
        }
    }
}
