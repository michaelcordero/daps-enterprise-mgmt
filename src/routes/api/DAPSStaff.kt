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
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

@KtorExperimentalLocationsAPI
@Location("/daps_staff")
class DAPSStaff

@ExperimentalTime
@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.daps_staff(){
    get<DAPSStaff>{
        try {
            log.info("GET /daps_staff requested")
            val time: TimedValue<Unit> = measureTimedValue {
                call.respond(status = HttpStatusCode.OK, message = cache.allDAPSStaff())
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    post<DAPSStaff>{
        try {
            log.info("POST /daps_staff requested")
            val dapsStaff: model.DAPSStaff = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val result: Int = cache.add(dapsStaff)
                val da = cache.allDAPSStaff().find { dsf -> dsf.initial == dapsStaff.initial }
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(da), "result" to result))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    put<DAPSStaff>{
       try {
           log.info("PUT /daps_staff requested")
           val dapsStaff: model.DAPSStaff = call.receive()
           val time: TimedValue<Unit> = measureTimedValue {
                val result: Int = cache.edit(dapsStaff)
                val ds = cache.allDAPSStaff().find { dsf -> dsf.initial == dapsStaff.initial }
               call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(ds), "result" to result))
           }
           log.info("Response took: ${time.duration}")
       } catch(e: Exception) {
           log.error(e.message)
           call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
       }
    }

    delete<DAPSStaff>{
        try {
            log.info("DELETE /daps_staff requested")
            val dapsStaff: model.DAPSStaff = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val result: Int = cache.remove(dapsStaff)
                call.respond(status = HttpStatusCode.OK,
                message = mapOf("data" to emptyList<DAPSStaff>(), "result" to result))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }


}
