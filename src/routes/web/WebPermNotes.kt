package routes.web

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import presenters.WebPermNotesPresenter
import java.time.LocalDateTime

@KtorExperimentalLocationsAPI

@Location("/webpermnotes")
class WebPermNotes

@KtorExperimentalLocationsAPI

fun Route.webpermnotes(presenter: WebPermNotesPresenter) {
    get<WebPermNotes> {
        call.respond(FreeMarkerContent("perm-notes.ftl", mapOf("presenter" to presenter), "perm-notes-e-tag:${LocalDateTime.now()}"))
    }
}
