package routes.web

import application.redirect
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import security.DAPSSession

@KtorExperimentalLocationsAPI
@Location("/")
class Index

/*
Register the index route of the app
 */
@KtorExperimentalLocationsAPI
fun Route.index() {
    // If the user has not already been authenticated we want to redirect to the login page otherwise dashboard.
    get<Index> {
        val session: DAPSSession? = call.sessions.get<DAPSSession>()
        if (session?.token != null ) {
            call.redirect(Welcome(session.emailId))
        } else {
            call.respond(FreeMarkerContent("weblogin.ftl", null, "someetag"))
        }
    }

    post<Index> {
        call.redirect(WebLogin())
    }
}
