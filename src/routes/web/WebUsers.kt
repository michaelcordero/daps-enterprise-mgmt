package routes.web

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import presenters.WebUsersPresenter
import java.time.LocalDateTime

@KtorExperimentalLocationsAPI
@Location("/webusers")
class Users

@KtorExperimentalLocationsAPI
fun Route.webusers(presenter: WebUsersPresenter) {
    get<Users>{
        call.respond(FreeMarkerContent("users.ftl", mapOf("presenter" to presenter), "users-etag:${LocalDateTime.now()}"))
    }
}
