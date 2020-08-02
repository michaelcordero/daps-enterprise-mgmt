package routes.web

import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.util.KtorExperimentalAPI
import presenters.WebTempNotesPresenter

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@Location("/webtempnotes")
class WebTempNotes

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
fun Route.webtempnotes(presenter: WebTempNotesPresenter) {
    get<WebTempNotes> {
        call.respond(FreeMarkerContent("tempnotes.ftl", mapOf("presenter" to presenter), "tempnotes-e-tag"))
    }
}
