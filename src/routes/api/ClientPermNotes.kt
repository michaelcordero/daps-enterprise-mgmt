package routes.api

import application.cache
import application.log
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.util.*
import model.ClientPermNote
import security.DAPSSession
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

@KtorExperimentalLocationsAPI
@Location("/client_perm_notes")
class ClientPermNotes

@ExperimentalTime
@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
fun Route.client_perm_notes() {
    // HTTP GET METHOD
    get<ClientPermNotes> {
        try {
            log.info("GET /client_perm_notes requested")
            val time: TimedValue<Unit> = measureTimedValue {
                call.respond(status = HttpStatusCode.OK, message = cache.client_perm_notes_map().values)
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    // HTTP POST METHOD
    post<ClientPermNotes> {
        try {
            log.info("POST /client_perm_notes requested")
            val client_perm_notes: ClientPermNote = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                val cpn: ClientPermNote = cache.add(client_perm_notes, session!!)
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(cpn)))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    // HTTP PUT METHOD
    put<ClientPermNotes> {
        try {
            log.info("PUT /client_perm_notes requested")
            val client_perm_notes: ClientPermNote = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.edit(client_perm_notes, session!!)
                val cpn = cache.client_perm_notes_map()[client_perm_notes.id]
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(cpn)))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    // HTTP DELETE METHOD
    delete<ClientPermNotes> {
        try {
            log.info("DELETE /client_perm_notes requested")
            val client_perm_notes: ClientPermNote = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.remove(client_perm_notes,session!!)
                call.respond(status = HttpStatusCode.OK, mapOf("data" to emptyList<ClientPermNotes>()))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }
}
