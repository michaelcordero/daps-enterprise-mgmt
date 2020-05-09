package routes.web

import database.queries.DataQuery
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import model.User

@KtorExperimentalLocationsAPI
@Location("/users")
class Users

@KtorExperimentalLocationsAPI
fun Route.users(dao: DataQuery){
    get<Users>{
        val users: List<User> = dao.allUsers()
        call.respond(FreeMarkerContent("users.ftl", mapOf("users" to users), "someetag"))
    }
}
