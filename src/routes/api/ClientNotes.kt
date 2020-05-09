package routes.api

import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import database.queries.DataQuery
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.locations.delete
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import io.ktor.util.KtorExperimentalAPI
import model.ClientNotes
import java.util.*

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

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
fun Route.clientNotes(dq: DataQuery) {
    get<ClientNotesRoute> {
        try {
            val client_notes: List<ClientNotes> = Collections.synchronizedList(dq.allClientNotes())
            call.respond(mapOf("all_client_notes" to synchronized(client_notes) { client_notes.toList()} ))
        } catch (e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = "Bad Request: $e")
        }
    }

    // Warning! This route does not work.
    get<ClientNotesRoute.ClientsNoteRouteByClientNum> {
        try {
            val one_client_notes: List<ClientNotes?> = dq.readClientsNotesByClientNum(it.client_num.toInt())
            call.respond(mapOf("one_clients_notes" to one_client_notes))
        } catch (e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = "Bad Request: ${e}")
            e.printStackTrace()
        }
    }

    get<ClientNotesRoute.ClientNoteByKey> {
        try {
            val client_note: ClientNotes? = dq.readClientNoteByKey(it.client_note_key.toInt())
            call.respond(mapOf("client_note" to client_note))
        } catch (e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = "Bad Request: $e")
        }
    }

    get<ClientNotesRoute.ClientNotesByInitial> {
        try {
            val client_notes: List<ClientNotes> = dq.readClientNotesByInitial(it.initial)
            call.respond(mapOf("client_notes" to client_notes))
        } catch(e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = "Bad Request: $e")
        }
    }

    post<ClientNotesRoute> {
        try {
            val clientNote: ClientNotes = call.receive(type = ClientNotes::class)
            val result: Int = dq.createClientNotes(clientNote)
            call.respond(status = HttpStatusCode.OK, message = mapOf("added client note" to true, "key" to result))
        } catch (e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = "Bad Request: $e")
        }
    }

    put<ClientNotesRoute> {
        try {
            val clientNote: ClientNotes = call.receive(type = ClientNotes::class)
            val result: Int = dq.updateClientNotes(clientNote)
            call.respond(status = HttpStatusCode.OK, message = mapOf("update client note" to true, "key" to result))
        } catch(e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = "Bad Request: $e")
        }
    }

    delete<ClientNotesRoute.ClientNoteByKey> {
        try {
            val result: Int = dq.deleteClientNote(it.client_note_key.toInt())
            call.respond(status = HttpStatusCode.OK, message = mapOf("delete client note" to true, "key" to result))
        } catch(e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = "Bad Request: $e")
        }
    }
}
