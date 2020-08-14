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
import model.PermReqNote
import security.DAPSSession
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
@Location("perm_req_notes")
class PermReqNotes

@ExperimentalTime
@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.perm_req_notes() {
    get<PermReqNotes> {
        try {
            log.info("GET /perm_req_notes requested")
            val time: TimedValue<Unit> = measureTimedValue {
                call.respond(status = HttpStatusCode.OK, message = cache.perm_req_notes_map().values)
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message = mapOf("error" to e.toString()))
        }
    }

    post<PermReqNotes> {
        try {
            log.info("POST /perm_req_notes requested")
            val perm_req_note: PermReqNote = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                val result: Int = cache.add(perm_req_note,session!!)
                val prn = cache.perm_req_notes_map()[result]
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(prn)))
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message = mapOf("error" to e.toString()))
        }
    }

    put<PermReqNotes> {
        try {
            log.info("PUT /perm_req_notes requested")
            val perm_req_note: PermReqNote = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.edit(perm_req_note,session!!)
                val prn = cache.perm_req_notes_map()[perm_req_note.id]
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(prn)))
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message = mapOf("error" to e.toString()))
        }
    }

    delete<PermReqNotes> {
        try {
            log.info("DELETE /perm_req_notes requested")
            val perm_req_note: PermReqNote = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.remove(perm_req_note,session!!)
                call.respond(
                    status = HttpStatusCode.OK,
                    message = mapOf("data" to emptyList<PermReqNote>())
                )
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message = mapOf("error" to e.toString()))
        }
    }
}
