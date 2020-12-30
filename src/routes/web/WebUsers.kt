package routes.web

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
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
