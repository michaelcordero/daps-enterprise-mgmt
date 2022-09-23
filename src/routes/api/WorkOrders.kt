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
import model.WorkOrder
import security.DAPSSession
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

@KtorExperimentalLocationsAPI
@Location("work_orders")
class WorkOrders

@ExperimentalTime
@KtorExperimentalLocationsAPI
fun Route.work_orders() {
    // HTTP GET METHOD
    get<WorkOrders> {
        try {
            log.info("GET /work_orders requested")
            val time: TimedValue<Unit> = measureTimedValue {
                call.respond(status = HttpStatusCode.OK, message = cache.work_orders_map().values)
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message = mapOf("error" to e.toString()))
        }
    }

    // HTTP POST METHOD
    post<WorkOrders> {
        try {
            log.info("POST /work_orders requested")
            val workOrder: WorkOrder = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                val wo: WorkOrder = cache.add(workOrder, session!!)
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(wo)))
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message = mapOf("error" to e.toString()))
        }
    }

    // HTTP PUT METHOD
    put<WorkOrders> {
        try {
            log.info("PUT /work_orders requested")
            val workOrder: WorkOrder = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.edit(workOrder, session!!)
                val wo: WorkOrder? = cache.work_orders_map()[workOrder.wo_number]
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(wo)))
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message = mapOf("error" to e.toString()))
        }
    }

    // HTTP DELETE METHOD
    delete<WorkOrders> {
        try {
            log.info("DELETE /work_orders requested")
            val workOrder: WorkOrder = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.remove(workOrder, session!!)
                call.respond(status = HttpStatusCode.OK, mapOf("data" to emptyList<WorkOrder>()))
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message = mapOf("error" to e.toString()))
        }
    }
}
