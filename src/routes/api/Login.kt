package routes.api

import application.cache
import application.dapsJWT
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.util.KtorExperimentalAPI
import model.User
import security.DAPSSecurity

@KtorExperimentalLocationsAPI
@Location("/login")
class Login

@KtorExperimentalLocationsAPI
@Location("/logout")
class Logout

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.login() {
    post<Login> {
        val post = call.receive<Parameters>()
        val user: User? = cache.allUsers().find { user ->
            user.email == post["user"] ?: "" && user.passwordHash == DAPSSecurity.hash(
                post["password"] ?: ""
            )
        }
        if (user != null) {
            call.respond(mapOf("token" to dapsJWT.sign(user.email)))
        } else {
            call.respond(status = HttpStatusCode.Unauthorized, message = "invalid credentials")
        }
    }

    post<Logout> {
        // remove the JWT? JWT's are nulled out on the client side.
        // however we could have an in-memory database of blacklisted JWT's?
        // With the time policy, of expiring JWT's after x amount of time, it's not necessary.
        call.respond(status = HttpStatusCode.OK, message = "logged out")
    }
}
