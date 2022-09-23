package routes.web

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import presenters.WebTempNotesPresenter
import java.time.LocalDateTime

@KtorExperimentalLocationsAPI

@Location("/webtempnotes")
class WebTempNotes

@KtorExperimentalLocationsAPI

fun Route.webtempnotes(presenter: WebTempNotesPresenter) {
    get<WebTempNotes> {
        call.respond(FreeMarkerContent("tempnotes.ftl", mapOf("presenter" to presenter), "tempnotes-e-tag:${LocalDateTime.now()}"))
    }
}
