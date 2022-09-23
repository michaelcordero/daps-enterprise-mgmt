package routes.web

import application.dapsJWT
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.freemarker.*
import io.ktor.server.locations.*
import io.ktor.server.locations.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import model.User
import presenters.WebLoginPresenter
import presenters.WelcomePresenter
import security.DAPSSession

@KtorExperimentalLocationsAPI
@Location("/weblogin")
data class WebLogin (val emailId: String = "", val error: String = "")


@KtorExperimentalLocationsAPI
fun Route.weblogin(presenter: WebLoginPresenter) {
    get<WebLogin> {
            call.respond(FreeMarkerContent("weblogin.ftl", mapOf("test" to "result", "presenter" to presenter), "someeetag"))
    }

    post<WebLogin> {
            val principal = call.principal<UserIdPrincipal>()
            if (principal != null) {
                val token = dapsJWT.sign(principal.name)
                call.sessions.set(DAPSSession(principal.name, token))
                val user: User = presenter.user(principal.name)!!
                call.respond(FreeMarkerContent("welcome.ftl", mapOf("user" to user, "presenter" to WelcomePresenter()), "someetag"))
            } else {
                call.respond(HttpStatusCode.Unauthorized, "unauthorized!")
            }
    }
}

