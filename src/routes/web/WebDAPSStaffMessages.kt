package routes.web

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import presenters.WebDAPSStaffMessagesPresenter
import java.time.LocalDateTime

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
                    "daps-staff-messages-e-tag:${LocalDateTime.now()}"
                )
            )
    }
}
