package routes.web

import application.redirect
import database.queries.DataQuery
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import model.User
import security.DAPSSession

@KtorExperimentalLocationsAPI
@Location("/users")
class Users

@KtorExperimentalLocationsAPI
fun Route.users(dao: DataQuery){
    get<Users>{
        val session: DAPSSession? = call.sessions.get<DAPSSession>()
        if (session?.token != null) {
            val users: List<User> = dao.allUsers()
            call.respond(FreeMarkerContent("users.ftl", mapOf("users" to users), "someetag"))
        } else {
            call.redirect(Index())
        }
    }
}
