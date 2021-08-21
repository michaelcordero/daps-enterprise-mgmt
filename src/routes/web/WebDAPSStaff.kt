package routes.web

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
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
