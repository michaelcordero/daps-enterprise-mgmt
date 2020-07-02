package routes.api

import application.log
import cache.DataCache
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.util.KtorExperimentalAPI
import model.ClientNotes
import java.util.*
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

@KtorExperimentalLocationsAPI
@Location("/client_notes")
class ClientNotesRoute {
    @Location("/client/{client_num}")
    data class ClientsNoteRouteByClientNum(val client_num: String, val clients: ClientNotesRoute)
    @Location("/note/{client_note_key}")
    data class ClientNoteByKey(val clients: ClientNotesRoute, val client_note_key: String)
    @Location("initial/{initial}")
    data class ClientNotesByInitial(val clients: ClientNotesRoute, val initial: String)
}

@ExperimentalTime
@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
fun Route.clientNotes(cache: DataCache) {
    get<ClientNotesRoute> {
        try {
            log.info("/client_notes requested")
            val time: TimedValue<Unit> = measureTimedValue {
                val client_notes: List<ClientNotes> = Collections.synchronizedList(cache.allClientNotes())
                call.respond(mapOf("all_client_notes" to synchronized(client_notes) { client_notes.toList()} ))
            }
            log.info("Response took: ${time.duration}")
        } catch (e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = mapOf("error" to e.message))
        }
    }

    // Warning! This route does not work.
    get<ClientNotesRoute.ClientsNoteRouteByClientNum> {
        try {
            val one_client_notes: List<ClientNotes?> =
                cache.allClientNotes().filter { clientNotes -> it.client_num.toInt() == clientNotes.client_num }
            call.respond(mapOf("one_clients_notes" to one_client_notes))
        } catch (e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = mapOf("error" to e.message))
        }
    }

    get<ClientNotesRoute.ClientNoteByKey> {
        try {
            val client_note: ClientNotes? =
                cache.allClientNotes().find { clientNotes -> it.client_note_key.toInt() == clientNotes.client_note_key }
            call.respond(mapOf("client_note" to client_note))
        } catch (e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = mapOf("error" to e.message))
        }
    }

    get<ClientNotesRoute.ClientNotesByInitial> {
        try {
            val client_notes: List<ClientNotes> =
                cache.allClientNotes().filter { clientNotes -> clientNotes.initial == it.initial }
            call.respond(mapOf("client_notes" to client_notes))
        } catch(e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = mapOf("error" to e.message))
        }
    }

    post<ClientNotesRoute> {
        try {
            val clientNote: ClientNotes = call.receive(type = ClientNotes::class)
            val result: Int = cache.add(clientNote)
            call.respond(status = HttpStatusCode.OK, message = mapOf("added client note" to true, "key" to result))
        } catch (e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = mapOf("error" to e.message))
        }
    }

    put<ClientNotesRoute> {
        try {
            val clientNote: ClientNotes = call.receive(type = ClientNotes::class)
            val result: Int = cache.edit(clientNote)
            call.respond(status = HttpStatusCode.OK, message = mapOf("update client note" to true, "key" to result))
        } catch(e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = mapOf("error" to e.message))
        }
    }

    delete<ClientNotesRoute.ClientNoteByKey> {
        try {
            val result: Int = cache.remove(it)
            val display: String = if (result == 1) "deleted" else "already deleted"
            call.respond(status = HttpStatusCode.OK, message = mapOf("deleted client note" to display))
        } catch(e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = mapOf("error" to e.message))
        }
    }
}
