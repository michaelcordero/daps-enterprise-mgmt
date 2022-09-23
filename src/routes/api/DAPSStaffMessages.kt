package routes.api

import application.cache
import application.log
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.locations.post
import io.ktor.server.locations.put
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import model.DAPSStaffMessage
import security.DAPSSession
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

@KtorExperimentalLocationsAPI
@Location("/daps_staff_messages")
class DAPSStaffMessages

@ExperimentalTime

@KtorExperimentalLocationsAPI
fun Route.daps_staff_messages() {
    // READ HTTP METHOD
    get<DAPSStaffMessages> {
        try {
            log.info("GET /daps_staff_messages")
            val time: TimedValue<Unit> = measureTimedValue {
                call.respond(status = HttpStatusCode.OK, message = cache.daps_staff_messages_map().values)
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    // CREATE HTTP METHOD
    post<DAPSStaffMessages>{
        try {
            log.info("POST /daps_staff_messages requested")
            val dsm: DAPSStaffMessage = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                val saved: DAPSStaffMessage = cache.add(dsm,session!!)
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(saved)))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    // UPDATE HTTP METHOD
    put<DAPSStaffMessages> {
        try {
            log.info("PUT /daps_staff_messages requested")
            val dsm: DAPSStaffMessage = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.edit(dsm,session!!)
                val edited: DAPSStaffMessage? = cache.daps_staff_messages_map()[dsm.staff_messages_key]
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(edited)))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    // DELETE HTTP METHOD
    delete<DAPSStaffMessages> {
        try {
            log.info("DELETE /daps_staff_messages requested")
            val dsm: DAPSStaffMessage = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.remove(dsm,session!!)
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to emptyList<DAPSStaffMessage>()))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }

    }
}
