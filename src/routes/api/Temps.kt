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
import model.Temp
import security.DAPSSession
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

@KtorExperimentalLocationsAPI
@Location("/temps")
class Temps

@ExperimentalTime
@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.temps() {
    // READ HTTP Method
    get<Temps> {
        try {
        log.info("GET /temps requested")
            val time: TimedValue<Unit> = measureTimedValue {
                call.respond(status = HttpStatusCode.OK, message = cache.temps_map().values)
            }
            log.info("Response took: ${time.duration}")
            } catch(e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = "Bad Request: $e")
        }
    }

    // CREATE HTTP Method
    post<Temps> {
        try {
            log.info("POST /temps requested")
            val temp: Temp = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                val result: Int = cache.add(temp,session!!)
                val saved: Temp? = cache.temps_map()[result]
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(saved)))
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message = mapOf("error" to e.toString()))
        }
    }

    // UPDATE HTTP Method
    put<Temps> {
        try {
            log.info("PUT /temps requested")
            val temp: Temp = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.edit(temp,session!!)
                val edited: Temp? = cache.temps_map()[temp.emp_num]
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(edited)))
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message = mapOf("error" to e.toString()))
        }
    }

    // DELETE HTTP Method
    delete<Temps> {
        try {
            log.info("DELETE /temps requested")
            val temp: Temp = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.remove(temp,session!!)
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to emptyList<Temp>()))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }
}
