package routes.web

import application.cache
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import model.User
import java.time.LocalDateTime

@KtorExperimentalLocationsAPI
@Location("/users")
class Users

@KtorExperimentalLocationsAPI
fun Route.users(){
    get<Users>{
        val users: List<User> = cache.users_map().values.toList()
        call.respond(FreeMarkerContent("users.ftl", mapOf("users" to users), "users-etag:${LocalDateTime.now()}"))
    }
}
