package routes.web

import application.log
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.util.*
import security.DAPSSession
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
@Location("/session")
class Session

@ExperimentalTime
@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.session() {
    // This method checks whether the user has a valid session. It is intended to be called from the client side.
    get<Session> {
        try {
            log.info("GET /session requested")
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                if (session != null) {
                    call.respond(status = HttpStatusCode.OK, message = "OK")
                } else {
//                    call.respond(Index())
                    call.respond(status = HttpStatusCode.Unauthorized, message = "NOT OK")
                }
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }
}
