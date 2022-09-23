package routes.web

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import presenters.WebDAPSStaffMessagesPresenter

@KtorExperimentalLocationsAPI

@Location("/webdapsstaffmessages")
class WebDAPSStaffMessages


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
