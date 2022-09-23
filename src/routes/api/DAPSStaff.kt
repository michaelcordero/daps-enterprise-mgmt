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
import security.DAPSSession
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

@KtorExperimentalLocationsAPI
@Location("/daps_staff")
class DAPSStaff

@ExperimentalTime

@KtorExperimentalLocationsAPI
fun Route.daps_staff(){
    get<DAPSStaff>{
        try {
            log.info("GET /daps_staff requested")
            val time: TimedValue<Unit> = measureTimedValue {
                call.respond(status = HttpStatusCode.OK, message = cache.daps_staff_map().values)
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
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.add(dapsStaff,session!!)
                val da = cache.daps_staff_map()[dapsStaff.initial]
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(da)))
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
               val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.edit(dapsStaff,session!!)
                val ds = cache.daps_staff_map()[dapsStaff.initial]
               call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(ds)))
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
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.remove(dapsStaff,session!!)
                call.respond(status = HttpStatusCode.OK,
                message = mapOf("data" to emptyList<DAPSStaff>()))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }


}
