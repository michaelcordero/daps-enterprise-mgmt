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
import model.Temp
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
                call.respond(status = HttpStatusCode.OK, message = cache.allTemps())
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
                val result: Int = cache.add(temp)
                val saved: Temp? = cache.allTemps().find { t -> t.emp_num == result }
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
                cache.edit(temp)
                val edited: Temp? = cache.allTemps().find { t -> t.emp_num == temp.emp_num }
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
                val result: Int = temp.let { cache.remove(temp) }
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to emptyList<Temp>(), "result" to result))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }
}
