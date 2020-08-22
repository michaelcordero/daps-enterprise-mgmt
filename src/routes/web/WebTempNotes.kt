package routes.web

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import presenters.WebTempNotesPresenter
import java.time.LocalDateTime

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@Location("/webtempnotes")
class WebTempNotes

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
fun Route.webtempnotes(presenter: WebTempNotesPresenter) {
    get<WebTempNotes> {
        call.respond(FreeMarkerContent("tempnotes.ftl", mapOf("presenter" to presenter), "tempnotes-e-tag:${LocalDateTime.now()}"))
    }
}
