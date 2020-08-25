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
import model.User
import presenters.WebResetPasswordPresenter
import java.time.LocalDateTime
import kotlin.random.Random
import kotlin.text.set

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@Location("/reset_password")
data class WebResetPassword(val email: String = "", val error: String = "")

@InternalAPI
@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.webresetpassword(presenter: WebResetPasswordPresenter) {
    // HTTP POST METHOD
    post<WebResetPassword> {
        // get post data
        val post_parameters = call.receive<Parameters>()
        val post_email: String = post_parameters["email"] ?: return@post call.redirect(it)
        try {
            // Let's check if this is a valid email
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
                        "reset-password:${LocalDateTime.now()}"
                    )
                )
            } else {
                // It is a valid email, so let's do the work
                val user: User = cache.users_map().values.first { u -> u.email == post_email }
                val temp_password_builder = StringBuilder("i1l45f9s012s47cJ7x0z")
                val random = Random(LocalDateTime.now().second)
                // Generate a temporary password
                val alphabet: CharArray = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray(0)
                val special_chars: CharArray = "#\$%".toCharArray(0)
                for (i in 0..19) {
                    // if even pick a letter
                    if (i % 2 == 0) {
                        when {
                            // Lowercase Letter
                            i % 4 == 0 -> {
                                temp_password_builder[i] = alphabet[random.nextInt(0, alphabet.size-26)]
                            }
                            // Capital Letter
                            i % 6 == 0 -> {
                                temp_password_builder[i] = alphabet[random.nextInt(26, alphabet.size-1)]
                            }
                            // Pick wherever
                            else -> {
                                temp_password_builder[i] = alphabet[random.nextInt(0, alphabet.size-1)]
                            }
                        }
                    }
                    // if odd pick an integer
                    if (i % 3 == 0) {
                        temp_password_builder[i] = random.nextInt(0,9).toString().first()
                    }
                    // pick a symbol at this index
                    if (i == 11) {
                        temp_password_builder[i] = special_chars[random.nextInt(0,special_chars.size-1)]
                    }
                }
                val temp_password = temp_password_builder.toString().substring(0..18)
                // Send email
                log.info("Sending email to: $user")
                log.info("Temporary password: $temp_password")
                log.info("Password length: ${temp_password.length}")
                call.respond(status = HttpStatusCode.OK, message = "")
            }
        } catch (e: Exception) {
            log.info(e.message)
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
