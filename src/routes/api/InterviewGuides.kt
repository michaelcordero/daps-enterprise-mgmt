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
import model.InterviewGuide
import security.DAPSSession
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@Location("/interview_guides")
class InterviewGuides

@ExperimentalTime
@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.interview_guides() {
    get<InterviewGuides> {
        try {
            log.info("GET /interview_guides requested")
            val time: TimedValue<Unit> = measureTimedValue {
                call.respond(status = HttpStatusCode.OK, message = cache.interview_guides_map().values)
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    post<InterviewGuides> {
        try {
            log.info("POST /interview_guides requested")
            val interview_guide: InterviewGuide = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                val result: Int = cache.add(interview_guide,session!!)
                val ig = cache.interview_guides_map()[result]
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(ig)))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    put<InterviewGuides> {
        try {
            log.info("PUT /interview_guides requested")
            val interview_guide: InterviewGuide = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.edit(interview_guide,session!!)
                val ig = cache.interview_guides_map()[interview_guide.id]
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(ig)))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    delete<InterviewGuides> {
        try {
            log.info("DELETE /interview_guides requested")
            val interview_guide: InterviewGuide = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.remove(interview_guide,session!!)
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to emptyList<InterviewGuide>()))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }
}
