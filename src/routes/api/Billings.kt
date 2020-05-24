package routes.api

import application.log
import cache.DataCache
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.util.KtorExperimentalAPI
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
fun Route.billings(cache: DataCache) {
    // Read HTTP Methods
    get<Billings> {
        try {
            log.info("/billings requested")
            val time: TimedValue<Unit> = measureTimedValue {
                call.respond(status = HttpStatusCode.OK, message = cache.allBilling())
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = "Bad Request: ${e}")
        }
    }

//    get<Billings.BillingsClient> {
//        try {
//            val billings: List<Billing> = Collections.synchronizedList(dq.billingByClient(it.client_num.toInt()))
//            call.respond(mapOf("billings" to synchronized(billings) {billings.toList()}))
//        } catch (e: Exception) {
//            call.respond(status = HttpStatusCode.BadRequest, message = "Bad Request: ${e}")
//        }
//    }
//
//    get<Billings.BillingsEmployee> {
//        try {
//            val billings: List<Billing> = Collections.synchronizedList(dq.billingByEmp(it.emp_num.toInt()))
//            call.respond(mapOf("billings" to synchronized(billings) {billings.toList()}))
//        } catch (e: Exception) {
//            call.respond(status = HttpStatusCode.BadRequest, message = "Bad Request: ${e}")
//        }
//    }
//
//    get<Billings.BillingsCounter> {
//        try {
//            val billings: List<Billing> = Collections.synchronizedList(dq.billingByCounter(it.counter.toInt()))
//            call.respond(mapOf("billings" to synchronized(billings) {billings.toList()}))
//        } catch (e: Exception) {
//            call.respond(status = HttpStatusCode.BadRequest, message = "Bad Request: ${e}")
//        }
//    }
//
//    // Create HTTP Method
//    post<Billings> {
//        try {
//            val billing: Billing = call.receive()
//            val result: Int = dq.createBilling(billing)
//            call.respond(status = HttpStatusCode.OK, message = mapOf("result" to result))
//        } catch (e: Exception) {
//            call.respond(status = HttpStatusCode.BadRequest, message = "Bad Request: ${e}")
//        }
//    }
//
//
//    // Update HTTP Method
//    put<Billings> {
//        try {
//            val billing: Billing = call.receive()
//            val result: Int = dq.updateBilling(billing)
//            call.respond(status = HttpStatusCode.OK, message = mapOf("result" to result))
//        } catch (e: Exception) {
//            call.respond(status = HttpStatusCode.BadRequest, message = "Bad Request: ${e}")
//        }
//    }
//
//    // Delete HTTP Method
//    delete<Billings.BillingsCounter> {
//        try {
//            val result: Int = dq.deleteBilling(it.counter.toInt())
//            call.respond(status = HttpStatusCode.OK, message = mapOf("deleted client" to true, "result" to result))
//        } catch (e: Exception) {
//            call.respond(status = HttpStatusCode.BadRequest, message = "Bad Request: ${e}")
//        }
//    }

}
