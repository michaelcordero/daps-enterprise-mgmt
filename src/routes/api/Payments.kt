package routes.api

import application.cache
import application.log
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.locations.post
import io.ktor.locations.put
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import model.Payment
import security.DAPSSession
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

@KtorExperimentalLocationsAPI
@Location("/payments")
class Payments

@ExperimentalTime
@KtorExperimentalLocationsAPI
fun Route.payments(){
    get<Payments>{
        try {
            log.info("GET /payments requested")
            val time: TimedValue<Unit> = measureTimedValue {
                call.respond(status = HttpStatusCode.OK, message = cache.payments_map().values)
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    post<Payments>{
        try {
            log.info("POST /payments requested")
            val payment: Payment = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                val pm: Payment = cache.add(payment,session!!)
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(pm)))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    put<Payments>{
        try {
            log.info("PUT /payments requested")
            val payment: Payment = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.edit(payment,session!!)
                val pm = cache.payments_map()[payment.ref_num]
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(pm)))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    delete<Payments>{
        try {
            log.info("DELETE /payments requested")
            val payment: Payment = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.remove(payment,session!!)
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to emptyList<Payment>()))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }
}
