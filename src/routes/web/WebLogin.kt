package routes.web

import io.ktor.application.call
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.principal
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import io.ktor.util.KtorExperimentalAPI
import presenters.WebLoginPresenter
import security.DAPSSession

@KtorExperimentalLocationsAPI
@Location("/weblogin")
data class WebLogin (val emailId: String = "", val error: String = "")

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.weblogin(presenter: WebLoginPresenter) {
    get<WebLogin> {
            call.respond(FreeMarkerContent("weblogin.ftl", null, "someeetag"))
    }

    post<WebLogin> {
            val principal = call.principal<UserIdPrincipal>()
            if (principal != null) {
                val token = presenter.dapsjwt.sign(principal.name)
                call.sessions.set(DAPSSession(principal.name, token))
                call.respond(FreeMarkerContent("welcome.ftl", mapOf("emailId" to principal.name), "someetag"))
            } else {
                call.respond(HttpStatusCode.Unauthorized, "unauthorized!")
            }
    }
}

