package routes.web

import application.dapsJWT
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.util.*
import model.User
import presenters.WebLoginPresenter
import presenters.WelcomePresenter
import security.DAPSSession
import java.time.LocalDateTime

@KtorExperimentalLocationsAPI
@Location("/weblogin")
data class WebLogin (val emailId: String = "", val error: String = "")

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.weblogin(presenter: WebLoginPresenter) {
    get<WebLogin> {
            call.respond(FreeMarkerContent("weblogin.ftl", mapOf("test" to "result", "presenter" to presenter), "login-etag:${LocalDateTime.now()}"))
    }

    post<WebLogin> {
            val principal = call.principal<UserIdPrincipal>()
            if (principal != null) {
                val token = dapsJWT.sign(principal.name)
                call.sessions.set(DAPSSession(principal.name, token))
                val user: User = presenter.user(principal.name)!!
                call.respond(FreeMarkerContent("welcome.ftl", mapOf("user" to user, "presenter" to WelcomePresenter()), "login-etag:${LocalDateTime.now()}"))
            } else {
                call.respond(HttpStatusCode.Unauthorized, "unauthorized!")
            }
    }
}

