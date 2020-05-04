package routes.web

import application.redirect
import io.ktor.application.application
import io.ktor.application.call
import io.ktor.application.log
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.Parameters
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import io.ktor.util.KtorExperimentalAPI
import model.User
import presenters.RegisterPresenter
import security.DAPSSession

@KtorExperimentalLocationsAPI
@Location("/register")
data class Register(
    val first_name: String = "",
    val last_name: String = "",
    val email: String = "",
    val error: String = ""
)

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.register(presenter: RegisterPresenter) {
    post<Register>{
        // get current user from session data if any
        val session: DAPSSession? = call.sessions.get<DAPSSession>()
        if (session?.token != null ) return@post call.redirect(Welcome(it.email))

        // get post data
        val registration = call.receive<Parameters>()
        val first_name: String = registration["first_name"] ?: return@post call.redirect(it)
        val last_name: String = registration["last_name"] ?: return@post call.redirect(it)
        val email: String = registration["email"] ?: return@post call.redirect(it)
        val password: String = registration["password"] ?: return@post call.redirect(it)

        // do biz work
        try {
            presenter.createUser(first_name, last_name, email, password)
            val token = presenter.dapsjwt.sign(email)
            call.sessions.set(DAPSSession(email,token))
            call.redirect(Welcome(email))
        } catch (e: Exception) {
            val error = Register(last_name, email, password)
            application.log.error("failed to register user", e)
            call.redirect(error.copy(error = e.cause.toString()))
        }
    }

    get<Register> {
        val session: DAPSSession? = call.sessions.get<DAPSSession>()
        if (session?.token != null ){
            call.respond(Welcome(it.email))
        } else {
            call.respond(FreeMarkerContent("register.ftl", mapOf("page_user" to User(0L,
                it.email, it.first_name,
                it.last_name,""
            ), "error" to it.error, "validator" to RegisterPresenter.Validator ), "some-etag"))
        }
    }
}

