package routes.web

import application.redirect
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import presenters.DashboardPresenter
import security.DAPSSession

@KtorExperimentalLocationsAPI
@Location("/dashboard")
class Dashboard

@KtorExperimentalLocationsAPI
fun Route.dashboard(presenter: DashboardPresenter) {
    get<Dashboard> {
        val session = call.sessions.get<DAPSSession>()
        if (session != null) {
            call.respond(FreeMarkerContent("dashboard.ftl", mapOf("user" to presenter.user(session.emailId), "presenter" to presenter), "dashboard_etag"))
        } else {
            call.respond(FreeMarkerContent("weblogin.ftl", mapOf("user" to "null"), "dashboard_etag"))
        }
    }
}
