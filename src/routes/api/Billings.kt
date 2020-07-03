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
import model.Billing
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

@KtorExperimentalLocationsAPI
@Location("/billings")
class Billings {
    @Location("/client/{client_num}")
    data class BillingsClient(val client_num: String, val billings: Billings)

    @Location("/employee/{emp_num}")
    data class BillingsEmployee(val emp_num: String, val billings: Billings)

    @Location("/counter/{counter}")
    data class BillingsCounter(val counter: String, val billings: Billings)
}

@ExperimentalTime
@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.billings() {
    // Read HTTP Methods
    get<Billings> {
        try {
            log.info("GET /billings requested")
            val time: TimedValue<Unit> = measureTimedValue {
                call.respond(status = HttpStatusCode.OK, message = cache.allBilling())
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = "Bad Request: $e")
        }
    }

    get<Billings.BillingsClient> {
        try {
            log.info("GET /billings/client_num=${it.client_num} requested")
            val time: TimedValue<Unit> = measureTimedValue {
                val billings: List<Billing> = cache.allBilling().filter {  b -> b.client_num == it.client_num.toInt()}
                call.respond(status = HttpStatusCode.OK, message = billings)
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = "Bad Request: $e")
        }
    }

    get<Billings.BillingsEmployee> {
        try {
            log.info("GET /billings/emp_num=${it.emp_num} requested")
            val time: TimedValue<Unit> = measureTimedValue {
                val billings: List<Billing> = cache.allBilling().filter { b -> b.employee_num == it.emp_num.toInt() }
                call.respond(status = HttpStatusCode.OK, message = billings)
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = "Bad Request: $e")
        }
    }

    get<Billings.BillingsCounter> {
        try {
            log.info("GET /billings/counter=${it.counter} requested")
            val time: TimedValue<Unit> = measureTimedValue {
                val billings: List<Billing> = cache.allBilling().filter { b -> b.counter == it.counter.toInt() }
                call.respond(status = HttpStatusCode.OK, message = billings)
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
                val result: Int = cache.add(billing)
                call.respond(status = HttpStatusCode.OK, message = mapOf("result" to result))
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = "Bad Request: $e")
        }
    }


    // Update HTTP Method
    put<Billings> {
        try {
            log.info("PUT /billings requested")
            val billing: Billing = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val result: Int = cache.edit(billing)
                call.respond(status = HttpStatusCode.OK, message = mapOf("result" to result))
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = "Bad Request: $e")
        }
    }

    // Delete HTTP Method
    delete<Billings.BillingsCounter> {
        try {
            log.info("DELETE /billings requested")
            val time: TimedValue<Unit> = measureTimedValue {
                val billing: Billing? = cache.allBilling().find { b -> b.counter == it.counter.toInt() }
                val result: Int = billing.let { cache.remove(billing) }
                call.respond(status = HttpStatusCode.OK, message = mapOf("deleted client" to true, "result" to result))
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = "Bad Request: $e")
        }
    }

}
