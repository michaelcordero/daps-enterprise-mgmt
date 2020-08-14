package routes.api

import application.cache
import application.log
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.util.KtorExperimentalAPI
import model.ClientFile
import security.DAPSSession
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
fun Route.clients() {
    get<Clients> {
        try {
            log.info("GET /clients requested")
            val time: TimedValue<Unit> = measureTimedValue {
                call.respond(status = HttpStatusCode.OK, message = cache.client_files_map().values)
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message = mapOf("error" to e.toString()))
        }
    }

    get<Clients.Client> {
        try {
            log.info("GET /clients/{client_num} requested")
            val time: TimedValue<Unit> = measureTimedValue {
                val client: ClientFile? = cache.client_files_map()[it.client_num.toInt()]
                call.respond(mapOf("client" to client))
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message = mapOf("error" to e.toString()))
        }
    }

    post<Clients> {
        try {
            log.info("POST /clients requested")
            val clientFile: ClientFile = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                val result: Int = cache.add(clientFile,session!!)
                val cfr = cache.client_files_map()[result]
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(cfr)))
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    put<Clients> {
        try {
            log.info("PUT /clients requested")
            val clientFile: ClientFile = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.edit(clientFile,session!!)
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(clientFile)))
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message = mapOf("error" to e.toString()))
        }
    }

    delete<Clients> {
        try {
            log.info("DELETE /clients requested")
            val clientFile: ClientFile = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.remove(clientFile,session!!)
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to emptyList<ClientFile>()))
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message = mapOf("error" to e.toString()))
        }
    }
}
