package routes.web

import application.cache
import application.log
import application.redirect
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.util.*
import model.User
import presenters.RegisterPresenter
import presenters.WebSettingsPresenter
import security.DAPSSecurity
import security.DAPSSession
import java.time.LocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@Location("/websettings")
data class WebSettings(val error: String = "")

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
@ExperimentalTime
fun Route.websettings(presenter: WebSettingsPresenter) {
    // HTTP GET METHOD
    get<WebSettings> {
        try {
            log.info("GET /websettings requested")
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                val user: User = cache.users_map().values.find { u -> u.email == session!!.emailId }!!
                call.respond(
                    FreeMarkerContent(
                        "settings.ftl",
                        mapOf("presenter" to presenter, "user" to user, "validator" to RegisterPresenter.Validator),
                        "settings-etag:${LocalDateTime.now()}"
                    )
                )
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }
    // HTTP POST METHOD
    post<WebSettings> {
        try {
            log.info("POST /websettings requested")
            val time: TimedValue<Unit> = measureTimedValue {
                // get post data
                val post = call.receive<Parameters>()
                // get session data
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                val old_user: User = cache.users_map().values.find { u -> u.email == session!!.emailId }!!
                val new_password: String? = post["new_password"]
                if (new_password != null) {
                    // We're changing the password
                    // Verify inputs
                    val verify_password: String = post["verify_password"] ?: return@post call.redirect(it)
                    val current_password: String = post["current_password"] ?: return@post call.redirect(it)
                    if (DAPSSecurity.hash(current_password) != old_user.passwordHash) {
                        call.respond(
                            FreeMarkerContent(
                                "settings.ftl",
                                mapOf(
                                    "presenter" to presenter,
                                    "user" to old_user,
                                    "validator" to RegisterPresenter.Validator,
                                    "error" to "incorrect current password"
                                ),
                                "settings-etag:${LocalDateTime.now()}"
                            )
                        )
                    } else if (verify_password != new_password) {
                        call.respond(
                            FreeMarkerContent(
                                "settings.ftl",
                                mapOf(
                                    "presenter" to presenter,
                                    "user" to old_user,
                                    "validator" to RegisterPresenter.Validator,
                                    "error" to "verify password & new password(s) do not match"
                                ),
                                "settings-etag:${LocalDateTime.now()}"
                            )
                        )
                    } else {
                        val edited_user: User = old_user.copy(passwordHash = DAPSSecurity.hash(new_password))
                        cache.edit(edited_user, session!!)
                        val saved_user: User = cache.users_map()[edited_user.id] ?: error("could not find user")
                        call.respond(
                            FreeMarkerContent(
                                "settings.ftl",
                                mapOf(
                                    "presenter" to presenter,
                                    "user" to saved_user,
                                    "validator" to RegisterPresenter.Validator,
                                    "password_updated" to "password updated"
                                ),
                                "settings-etag:${LocalDateTime.now()}"
                            )
                        )
                    }
                } else {
                    // We're changing other attributes
                    val firstName: String =
                        if (post["firstname"] != "" && post["firstname"] != null) post["firstname"]!! else old_user.first_name
                    val lastName: String =
                        if (post["lastname"] != "" && post["lastname"] != null) post["lastname"]!! else old_user.last_name
                    val email: String = if (post["email"] != "" && post["email"] != null) post["email"]!! else old_user.email
                    val edited_user: User = old_user.copy(first_name = firstName, last_name = lastName, email = email)
                    cache.edit(edited_user, session!!)
                    val saved_user: User? = cache.users_map()[edited_user.id]
                    call.respond(
                        FreeMarkerContent(
                            "settings.ftl",
                            mapOf("presenter" to presenter, "user" to saved_user, "validator" to RegisterPresenter.Validator,
                                "user_updated" to "user updated"),
                            "settings-etag:${LocalDateTime.now()}"
                        )
                    )
                }
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }
}
