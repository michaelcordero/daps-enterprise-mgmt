package routes.web

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
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
