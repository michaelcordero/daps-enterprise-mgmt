package routes.web

import application.cache
import application.log
import application.redirect
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.locations.post
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.Route
import io.ktor.util.*
import presenters.WebResetPasswordPresenter
import java.time.LocalDateTime

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@Location("/reset_password")
data class WebResetPassword(val email: String = "", val error: String = "")

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.webresetpassword(presenter: WebResetPasswordPresenter) {
    // HTTP POST METHOD
    post<WebResetPassword> {
        // get post data
        val post_parameters = call.receive<Parameters>()
        val post_email: String = post_parameters["email"] ?: return@post call.redirect(it)
        try {
            // Let's check if this is a valid email within the app
            if (!cache.users_map().values.stream().anyMatch { user -> user.email == post_email }) {
                val wrp = WebResetPassword(email = post_email, error = "$post_email is not registered with any user")
                call.respond(
                    FreeMarkerContent(
                        "reset-password.ftl",
                        mapOf(
                            "wrp" to wrp,
                            "presenter" to presenter,
                            "validator" to WebResetPasswordPresenter.Validator
                        ),
                        "reset-password-etag:${LocalDateTime.now()}"
                    )
                )
            } else {
                // It is a valid email within the app, so let's do the work
                cache.reset_password(post_email)
                call.respond(
                    FreeMarkerContent(
                        "reset-password-successful.ftl",
                        mapOf("presenter" to presenter), "reset-password-successful-etag:${LocalDateTime.now()}"
                    )
                )
            }
        } catch (e: Exception) {
            log.info(e.message)
            val wrp = WebResetPassword(
                email = post_email,
                error = "Password reset failed. Error: ${e.message}. Please try again."
            )
            call.respond(
                FreeMarkerContent(
                    "reset-password.ftl",
                    mapOf(
                        "wrp" to wrp,
                        "presenter" to presenter,
                        "validator" to WebResetPasswordPresenter.Validator
                    ),
                    "reset-password-etag:${LocalDateTime.now()}"
                )
            )
        }
    }

    // HTTP GET METHOD
    get<WebResetPassword> {
        call.respond(
            FreeMarkerContent(
                "reset-password.ftl",
                mapOf("wrp" to it, "presenter" to presenter, "validator" to WebResetPasswordPresenter.Validator),
                "reset-password:${LocalDateTime.now()}"
            )
        )
    }
}
