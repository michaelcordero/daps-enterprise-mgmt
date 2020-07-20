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
import model.TempNote
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

@KtorExperimentalLocationsAPI
@Location("/tempnotes")
class TempNotes

@ExperimentalTime
@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.tempnotes() {
    // READ HTTP Method
    get<TempNotes> {
        try {
            log.info("/tempnotes requested")
            val time: TimedValue<Unit> = measureTimedValue {
                call.respond(status = HttpStatusCode.OK, message = cache.allTempNotes())
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    // CREATE HTTP Method
    post<TempNotes> {
        try {
            log.info("POST /tempnotes requested")
            val temp_note: TempNote = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val result: Int = cache.add(temp_note)
                val saved: TempNote? = cache.allTempNotes().find { tn -> tn.temp_note_key == result}
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(saved)))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    // UPDATE HTTP Method
    put<TempNotes> {
        try {
            log.info("PUT /tempnotes requested")
            val temp_note: TempNote = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                cache.edit(temp_note)
                val edited: TempNote? = cache.allTempNotes().find { tn -> tn.temp_note_key == temp_note.temp_note_key }
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(edited)))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    // DELETE HTTP Method
    delete<TempNotes> {
        try {
            log.info("DELETE /tempnotes requested")
            val tempNote: TempNote = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val result: Int = tempNote.let {  cache.remove(tempNote) }
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to emptyList<TempNote>(), "result" to result))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }


}
