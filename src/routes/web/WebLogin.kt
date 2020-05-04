package routes.web

import application.redirect
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.Parameters
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.sessions.clear
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import io.ktor.util.KtorExperimentalAPI
import model.User
import presenters.WebLoginPresenter
import security.DAPSSession

@KtorExperimentalLocationsAPI
@Location("/weblogin")
data class WebLogin (val emailId: String = "", val error: String = "")

@KtorExperimentalLocationsAPI
@Location("/weblogout")
class WebLogout

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.weblogin(presenter: WebLoginPresenter) {
    get<WebLogin> {
        val session: DAPSSession? = call.sessions.get<DAPSSession>()
        if (session?.token != null) {
            call.redirect(Welcome(session.emailId))
        } else {
            call.respond(FreeMarkerContent("weblogin.ftl", mapOf("emailId" to it.emailId, "error" to it.error), "someeetag"))
        }
    }

    post<WebLogin> {
        val post: Parameters = call.receive()
        val password = post["password"] ?: return@post call.redirect(it)
        val emailId = post["emailId"] ?: return@post call.redirect(it)
        val error = WebLogin(emailId, "")
        val user: User? = presenter.user(emailId)

        if (user == null) {
            call.redirect(error.copy(error = "Invalid email"))
        } else if (user.passwordHash != presenter.hashPassword(password)){
            call.redirect(error.copy(error = "Invalid password"))
        } else {
            val token = presenter.dapsjwt.sign(user.email)
            call.sessions.set(DAPSSession(user.email,token))
            call.redirect(Welcome(user.email))
        }
    }

    get<WebLogout> {
        call.sessions.clear<DAPSSession>()
        call.redirect(Index())
    }
}

