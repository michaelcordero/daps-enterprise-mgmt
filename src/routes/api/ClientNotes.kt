package routes.api

import application.cache
import application.log
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.util.KtorExperimentalAPI
import model.ClientNote
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
                call.respond(status = HttpStatusCode.OK, message = cache.allClientNotes())
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
                val result: Int = cache.add(client_note)
                val cn = cache.allClientNotes().find { cno -> cno.client_note_key == result }
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
                val result: Int = cache.edit(client_note)
                val cn = cache.allClientNotes().find { cno -> cno.client_note_key == client_note.client_note_key }
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(cn), "result" to result))
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
                val result: Int = cache.remove(client_note)
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to emptyList<ClientNote>(), "result" to result))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }
}
