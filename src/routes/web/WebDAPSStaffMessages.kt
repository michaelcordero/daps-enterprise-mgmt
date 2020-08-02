package routes.web

import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.util.KtorExperimentalAPI
import presenters.WebDAPSStaffMessagesPresenter

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@Location("/webdapsstaffmessages")
class WebDAPSStaffMessages

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.webdapsstaffmessages(presenter: WebDAPSStaffMessagesPresenter) {
    get<WebDAPSStaffMessages> {
            call.respond(
                FreeMarkerContent(
                    "daps-staff-messages.ftl",
                    mapOf("presenter" to presenter),
                    "daps-staff-messages-e-tag"
                )
            )
    }
}
