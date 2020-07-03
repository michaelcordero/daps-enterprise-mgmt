package routes.web

import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.util.KtorExperimentalAPI
import presenters.WebLoginPresenter
import presenters.WebTempNotesPresenter
import security.DAPSSession

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@Location("/webtempnotes")
class WebTempNotes

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
fun Route.webtempnotes(presenter: WebTempNotesPresenter){
    get<WebTempNotes>{
        try {
            val session: DAPSSession? = call.sessions.get<DAPSSession>()
            if (session != null) {
                call.respond(FreeMarkerContent("tempnotes.ftl", mapOf("presenter" to presenter), "tempnotes-e-tag"))
            } else {
                call.respond(FreeMarkerContent("weblogin.ftl", mapOf("user" to "null", "presenter" to WebLoginPresenter()), "web-tempnotes-e-tag"))
            }
        } catch(e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = "Bad Request: $e")
        }
    }
}
