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
import model.PermNote
import security.DAPSSession
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue


@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@Location("/perm_notes")
class PermNotes

@ExperimentalTime
@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.perm_notes() {
    get<PermNotes> {
        try {
            log.info("GET /perm_notes requested")
            val time: TimedValue<Unit> = measureTimedValue {
                call.respond(status = HttpStatusCode.OK, message = cache.perm_notes_map().values)
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    post<PermNotes> {
        try {
            log.info("POST /perm_notes requested")
            val perm_note: PermNote = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                val result: Int = cache.add(perm_note,session!!)
                val pn = cache.perm_notes_map()[result]
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(pn)))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    put<PermNotes> {
        try {
            log.info("PUT /perm_notes requested")
            val perm_note: PermNote = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.edit(perm_note,session!!)
                val pn = cache.perm_notes_map()[perm_note.id]
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(pn)))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    delete<PermNotes> {
        try {
            log.info("DELETE /perm_notes requested")
            val perm_note: PermNote = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.remove(perm_note,session!!)
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to emptyList<PermNote>()))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }
}
