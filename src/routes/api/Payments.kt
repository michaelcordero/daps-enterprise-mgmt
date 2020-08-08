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
import model.Payment
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@Location("/payments")
class Payments

@ExperimentalTime
@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.payments(){
    get<Payments>{
        try {
            log.info("GET /payments requested")
            val time: TimedValue<Unit> = measureTimedValue {
                call.respond(status = HttpStatusCode.OK, message = cache.allPayments())
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
                val result: Int = cache.add(payment)
                val pm = cache.allPayments().find { p -> p.ref_num == payment.ref_num }
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(pm), "result" to result))
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
                val result: Int = cache.edit(payment)
                val pm = cache.allPayments().find { p -> p.ref_num == payment.ref_num }
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(pm), "result" to result))
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
                val result: Int = cache.remove(payment)
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to emptyList<Payment>(), "result" to result))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }
}
