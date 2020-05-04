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
import presenters.WelcomePresenter
import security.DAPSSession

/*
Register the welcome route of the app
 */
@KtorExperimentalLocationsAPI
@Location("/welcome/{emailId}")
data class Welcome(val emailId: String)

// This uses the @Location feature to register the get route for '/welcome'.
@KtorExperimentalLocationsAPI
fun Route.welcome(presenter: WelcomePresenter) {
    get<Welcome> {
        val session: DAPSSession? = call.sessions.get<DAPSSession>()
        if ( session?.token != null ) {
            call.respond(FreeMarkerContent("welcome.ftl", mapOf("emailId" to session.emailId ), "someetag"))
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Unauthenticated Session Request")
        }
    }
}
