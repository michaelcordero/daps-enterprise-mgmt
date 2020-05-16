package routes.api

import application.log
import cache.InMemoryCache
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.util.KtorExperimentalAPI
import model.Billing

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

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.billings(cache: InMemoryCache) {
    // Read HTTP Methods
    get<Billings> {
        try {
            val billings: List<Billing> = cache.billings
            log.info("/billings requested")
            call.respond(status = HttpStatusCode.OK, message = billings)
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
