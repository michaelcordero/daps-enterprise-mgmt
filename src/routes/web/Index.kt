package routes.web

import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
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
            call.respond(FreeMarkerContent("dashboard.ftl", mapOf("emailId" to session.emailId), "someetag"))
        } else {
            call.respond(FreeMarkerContent("weblogin.ftl", mapOf("emailId" to null), "someetag"))
        }
    }
}
