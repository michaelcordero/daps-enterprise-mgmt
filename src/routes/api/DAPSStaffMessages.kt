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
import model.DAPSStaffMessage
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

@KtorExperimentalLocationsAPI
@Location("/daps_staff_messages")
class DAPSStaffMessages

@ExperimentalTime
@KtorExperimentalAPI
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
                val result: Int = cache.add(dsm)
                val saved: DAPSStaffMessage? = cache.daps_staff_messages_map()[result]
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
                cache.edit(dsm)
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
            val dsm: DAPSStaffMessage? = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                cache.remove(dsm)
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to emptyList<DAPSStaffMessage>()))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }

    }
}
