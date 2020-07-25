package routes.web

import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.util.KtorExperimentalAPI
import presenters.WebDAPSStaffMessagesPresenter
import presenters.WebLoginPresenter
import security.DAPSSession

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@Location("/webdapsstaffmessages")
class WebDAPSStaffMessages

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.webdapsstaffmessages(presenter: WebDAPSStaffMessagesPresenter) {
    get<WebDAPSStaffMessages> {
        val session: DAPSSession? = call.sessions.get<DAPSSession>()
        if (session != null) {
            call.respond(
                FreeMarkerContent(
                    "daps-staff-messages.ftl",
                    mapOf("presenter" to presenter),
                    "daps-staff-messages-e-tag"
                )
            )
        } else {
            call.respond(FreeMarkerContent("weblogin.ftl", mapOf("presenter" to WebLoginPresenter()), "web-daps-staff-messages-e-tag"))
        }
    }
}
