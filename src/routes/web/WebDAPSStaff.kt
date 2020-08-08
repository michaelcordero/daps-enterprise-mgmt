package routes.web

import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.util.KtorExperimentalAPI
import presenters.WebDAPSStaffPresenter
import java.time.LocalDateTime

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@Location("/webdapsstaff")
class WebDAPSStaff

@KtorExperimentalAPI
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
