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
import model.WONote
import security.DAPSSession
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
@Location("/work_order_notes")
class WorkOrderNotes

@ExperimentalTime
@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
fun Route.work_order_notes() {
    // HTTP GET METHOD
    get<WorkOrderNotes> {
        try {
            log.info("GET /work_order_notes requested")
            val time: TimedValue<Unit> = measureTimedValue {
                call.respond(status = HttpStatusCode.OK, message = cache.wo_notes_map().values)
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    // HTTP POST METHOD
    post<WorkOrderNotes> {
        try {
            log.info("POST /work_order_notes requested")
            val woNote: WONote = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                val won: WONote = cache.add(woNote,session!!)
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(won)))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    // HTTP PUT METHOD
    put<WorkOrderNotes> {
        try {
            log.info("PUT /work_order_notes requested")
            val woNote: WONote = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.edit(woNote, session!!)
                val won: WONote? = cache.wo_notes_map()[woNote.id]
                call.respond(status = HttpStatusCode.OK, mapOf("data" to listOf(won)))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    // HTTP DELETE METHOD
    delete<WorkOrderNotes> {
        try {
            log.info("DELETE /work_order_notes requested")
            val woNote: WONote = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.remove(woNote,session!!)
                call.respond(status = HttpStatusCode.OK, mapOf("data" to emptyList<WONote>()))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }
}
