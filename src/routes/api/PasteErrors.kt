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
import model.PasteError
import security.DAPSSession
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

@KtorExperimentalLocationsAPI
@Location("/paste_errors")
class PasteErrors

@ExperimentalTime
@KtorExperimentalLocationsAPI
fun Route.paste_errors() {
    // HTTP GET METHOD
    get<PasteErrors> {
        try {
            log.info("GET /paste_errors requested")
            val time: TimedValue<Unit> = measureTimedValue {
                call.respond(status = HttpStatusCode.OK, message = cache.paste_errors_map().values)
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message = mapOf("error" to e.toString()))
        }
    }

    // HTTP POST METHOD
    post<PasteErrors> {
        try {
            log.info("POST /paste_errors requested")
            val pasteError: PasteError = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                val pe: PasteError = cache.add(pasteError, session!!)
                call.respond(status = HttpStatusCode.OK, mapOf("data" to listOf(pe)))
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message = mapOf("error" to e.toString()))
        }
    }

    // HTTP PUT METHOD
    put<PasteErrors> {
        try {
            log.info("PUT /paste_errors requested")
            val pasteError: PasteError = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.edit(pasteError, session!!)
                val pe: PasteError? = cache.paste_errors_map()[pasteError.ref_num]
                call.respond(HttpStatusCode.OK, mapOf("data" to listOf(pe)))
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message = mapOf("error" to e.toString()))
        }
    }

    // HTTP DELETE METHOD
    delete<PasteErrors> {
        try {
            log.info("DELETE /paste_errors requested")
            val pasteError: PasteError = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.remove(pasteError, session!!)
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to emptyList<PasteError>()))
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message = mapOf("error" to e.toString()))
        }
    }
}
