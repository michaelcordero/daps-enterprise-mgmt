package routes.web

import application.dapsJWT
import application.redirect
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.locations.post
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.Route
import io.ktor.sessions.*
import model.User
import presenters.RegisterPresenter
import security.DAPSRole
import security.DAPSSession

@KtorExperimentalLocationsAPI
@Location("/register")
data class Register(
    val first_name: String = "",
    val last_name: String = "",
    val email: String = "",
    val error: String = ""
)

@KtorExperimentalLocationsAPI
fun Route.register(presenter: RegisterPresenter) {
    post<Register> {
        // get current user from session data if any
        val session: DAPSSession? = call.sessions.get<DAPSSession>()
        if (session?.sessionId != null) return@post call.redirect(Welcome())

        // get post data
        val registration = call.receive<Parameters>()
        val first_name: String = registration["first_name"] ?: return@post call.redirect(it)
        val last_name: String = registration["last_name"] ?: return@post call.redirect(it)
        val email: String = registration["email"] ?: return@post call.redirect(it)
        val password: String = registration["password"] ?: return@post call.redirect(it)
        val registration_key: String = registration["key"] ?: return@post call.redirect(it)

        // do biz work
        try {
            val token = dapsJWT.sign(email)
            call.sessions.set(DAPSSession(email, token))
            val updated_session = call.sessions.get<DAPSSession>()
            presenter.createUser(first_name, last_name, email, password, updated_session!!, registration_key)
            call.redirect(Welcome())
        } catch (e: Exception) {
            val error = Register(last_name, email, password)
            val invalid_session: DAPSSession? = call.sessions.get<DAPSSession>()
            invalid_session?.sessionId = null
            call.sessions.clear<DAPSSession>()
            application.log.error("failed to register user", e)
            call.redirect(error.copy(error = e.message.toString()))
        }
    }

    get<Register> {
        call.respond(
            FreeMarkerContent(
                "register.ftl", mapOf(
                    "page_user" to User(
                        0L,
                        it.email, it.first_name,
                        it.last_name, "", DAPSRole.CLIENT
                    ),
                    "error" to it.error,
                    "validator" to RegisterPresenter.Validator,
                    "presenter" to RegisterPresenter()
                ), "some-etag"
            )
        )
    }
}

