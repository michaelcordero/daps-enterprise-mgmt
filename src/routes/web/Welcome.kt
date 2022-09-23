package routes.web

import io.ktor.http.*
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

/*
Register the welcome route of the app
 */
@KtorExperimentalLocationsAPI
@Location("/welcome")
class Welcome

@KtorExperimentalLocationsAPI
fun Route.welcome(presenter: WelcomePresenter) {
    get<Welcome> {
        val session: DAPSSession? = call.sessions.get<DAPSSession>()
        if (session != null) {
            val user: User? = presenter.user(session.emailId)
            if(user != null ){
                call.respond(FreeMarkerContent("welcome.ftl", mapOf("user" to user, "presenter" to presenter), "welcome-e-tag"))
            } else {
                call.respond(HttpStatusCode.Unauthorized, "User not found!")
            }
        } else {
            call.respond(FreeMarkerContent("weblogin.ftl", mapOf("emailId" to null, "presenter" to WebLoginPresenter()), "welcome-e-tag"))
        }
    }
}
