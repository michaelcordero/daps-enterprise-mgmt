package routes.web

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
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
