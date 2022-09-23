package routes.web

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import presenters.WebDAPSStaffPresenter
import java.time.LocalDateTime

@KtorExperimentalLocationsAPI

@Location("/webdapsstaff")
class WebDAPSStaff


@KtorExperimentalLocationsAPI
fun Route.webdapsstaff(presenter: WebDAPSStaffPresenter) {
    get<WebDAPSStaff> {
        call.respond(
            FreeMarkerContent(
                "daps-staff.ftl",
                mapOf("presenter" to presenter),
                "daps-staff-e-tag:${LocalDateTime.now()}"
            )
        )
    }
}
