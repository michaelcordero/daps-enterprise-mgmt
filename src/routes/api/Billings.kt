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
import model.Billing
import security.DAPSSession
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

@KtorExperimentalLocationsAPI
@Location("/billings")
class Billings

@ExperimentalTime
@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.billings() {
    // Read HTTP Methods
    get<Billings> {
        try {
            log.info("GET /billings requested")
            val time: TimedValue<Unit> = measureTimedValue {
                call.respond(status = HttpStatusCode.OK, message = cache.billings_map().values)
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = "Bad Request: $e")
        }
    }

    // Create HTTP Method
    post<Billings> {
        try {
            log.info("POST /billings requested")
            val billing: Billing = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                val result: Int = cache.add(billing,session!!)
                val br: Billing? = cache.billings_map()[result]
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(br)))
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }


    // Update HTTP Method
    put<Billings> {
        try {
            log.info("PUT /billings requested")
            val billing: Billing = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.edit(billing, session!!)
                val br: Billing? =
                    cache.billings_map()[billing.counter] // need to query! otherwise object will come back without previous data.
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(br)))
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    // Delete HTTP Method
    delete<Billings> {
        try {
            log.info("DELETE /billings requested")
            val billing: Billing = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.remove(billing,session!!)
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to emptyList<Billing>()))
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

}
