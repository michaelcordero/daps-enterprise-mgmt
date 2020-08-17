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
import model.ClientNote
import security.DAPSSession
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

@KtorExperimentalLocationsAPI
@Location("/client_notes")
class ClientNotes

@ExperimentalTime
@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
fun Route.client_notes() {
    get<ClientNotes> {
        try {
            log.info("GET /client_notes requested")
            val time: TimedValue<Unit> = measureTimedValue {
                call.respond(status = HttpStatusCode.OK, message = cache.client_notes_map().values)
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message = mapOf("error" to e.toString()))
        }
    }

    post<ClientNotes> {
        try {
            log.info("POST /client_notes requested")
            val client_note: ClientNote = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                val cn: ClientNote = cache.add(client_note,session!!)
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(cn)))
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message = mapOf("error" to e.toString()))
        }
    }

    put<ClientNotes> {
        try {
            log.info("PUT /client_notes requested")
            val client_note: ClientNote = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.edit(client_note, session!!)
                val cn = cache.client_notes_map()[client_note.client_note_key]
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(cn)))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    delete<ClientNotes> {
        try {
            log.info("DELETE /client_notes requested")
            val client_note: ClientNote = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.remove(client_note, session!!)
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to emptyList<ClientNote>()))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }
}
