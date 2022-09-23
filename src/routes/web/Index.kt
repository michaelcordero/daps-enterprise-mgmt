package routes.web

import application.cache
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import model.User
import presenters.WebLoginPresenter
import presenters.WelcomePresenter
import security.DAPSSession

@KtorExperimentalLocationsAPI
@Location("")
class Index

/*
Register the index route of the app
 */
@KtorExperimentalLocationsAPI
fun Route.index() {
    // If the user has not already been authenticated we want to redirect to the login page otherwise dashboard.
    get<Index> {
        val session: DAPSSession? = call.sessions.get<DAPSSession>()
        if (session?.sessionId != null) {
            val user: User? = cache.users_map().values.find { user -> user.email == session.emailId }
            call.respond(
                FreeMarkerContent(
                    "welcome.ftl",
                    mapOf("emailId" to session.emailId, "presenter" to WelcomePresenter(), "user" to user),
                    "welcome-etag"
                )
            )
        } else {
            call.respond(
                FreeMarkerContent(
                    "weblogin.ftl",
                    mapOf("emailId" to null, "presenter" to WebLoginPresenter()),
                    "web-login-etag"
                )
            )
        }
    }
}
