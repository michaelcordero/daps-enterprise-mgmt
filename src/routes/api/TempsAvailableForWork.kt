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
import model.TempsAvail4Work
import security.DAPSSession
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

@KtorExperimentalLocationsAPI
@Location("temps_available_for_work")
class TempsAvailableForWork

@ExperimentalTime
@KtorExperimentalLocationsAPI
fun Route.temps_available_for_work() {
    // HTTP GET METHOD
    get<TempsAvailableForWork> {
        try {
            log.info("GET /temps_available_for_work requested")
            val time: TimedValue<Unit> = measureTimedValue {
                call.respond(status = HttpStatusCode.OK, message = cache.temps_avail_for_work_map().values)
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }
    // HTTP POST METHOD
    post<TempsAvailableForWork> {
        try {
            log.info("POST /temps_available_for_work requested")
            val ta4w: TempsAvail4Work = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                val tempsAvail4Work: TempsAvail4Work = cache.add(ta4w, session!!)
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(tempsAvail4Work)))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    // HTTP PUT METHOD
    put<TempsAvailableForWork> {
        try {
            log.info("PUT /temps_available_for_work requested")
            val ta4w: TempsAvail4Work = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.edit(ta4w,session!!)
                val tempsAvail4Work = cache.temps_avail_for_work_map()[ta4w.rec_num]
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(tempsAvail4Work)))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    // HTTP DELETE METHOD
    delete<TempsAvailableForWork> {
        try {
            log.info("DELETE /temps_available_for_work requested")
            val ta4w: TempsAvail4Work = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.remove(ta4w,session!!)
                call.respond(status = HttpStatusCode.OK, mapOf("data" to emptyList<TempsAvail4Work>()))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

}
