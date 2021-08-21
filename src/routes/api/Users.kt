package routes.api

import application.cache
import application.log
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.locations.post
import io.ktor.locations.put
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import model.User
import security.DAPSRole
import security.DAPSSecurity
import security.DAPSSession
import kotlin.random.Random
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

@KtorExperimentalLocationsAPI
@Location("/users")
class Users {
    companion object Processor {
        fun processUser(user_input: User) : User {
            var passwordHash: String = user_input.passwordHash
            // in order to know if add or edit, we query for user with same id
            var id: Long = user_input.id
            val old: User? = cache.users_map()[id]
            if (old == null) { // add new user context
                passwordHash = DAPSSecurity.hash(user_input.passwordHash)
                id = Random.nextLong(from = 1000000, until = 1999999)
                while (cache.users_map().containsKey(id)) { // need a new id
                    id = Random.nextLong(from = 1000000, until = 1999999)
                }
            } else { // edit existing user context
                if (old.passwordHash != user_input.passwordHash) {
                    passwordHash = DAPSSecurity.hash(user_input.passwordHash)
                }
                // otherwise leave as is, there is no password change in the edit context
            }
            val role: DAPSRole = when (user_input.role.toString()) {
                "ADMIN", "Admin", "admin" -> DAPSRole.ADMIN
                "CLIENT", "Client", "client" -> DAPSRole.CLIENT
                "STAFF", "Staff", "staff" -> DAPSRole.STAFF
                else -> throw IllegalArgumentException("Incorrect role assignment. Must be one of the following: ADMIN, CLIENT, or STAFF")
            }
            return user_input.copy(id= id,passwordHash = passwordHash, role = role)
        }
    }
}

@ExperimentalTime
@KtorExperimentalLocationsAPI
fun Route.users() {
    // READ HTTP METHOD
    get<Users> {
        try {
            log.info("GET /users requested")
            val time: TimedValue<Unit> = measureTimedValue {
                call.respond(status = HttpStatusCode.OK, message = cache.users_map().values)
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    // CREATE HTTP METHOD
    post<Users> {
        try {
            log.info("POST /post requested")
            val user_input: User = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                val saved: User = cache.add(Users.processUser(user_input), session!!)
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(saved)))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    // UPDATE HTTP METHOD
    put<Users> {
        try {
            log.info("PUT /users requested")
            val user_input: User = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.edit(Users.processUser(user_input), session!!)
                val edited: User? = cache.users_map()[user_input.id]
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to listOf(edited)))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }

    // DELETE HTTP METHOD
    delete<Users> {
        try {
            log.info("DELETE /users requested")
            val user: User = call.receive()
            val time: TimedValue<Unit> = measureTimedValue {
                val session: DAPSSession? = call.sessions.get<DAPSSession>()
                cache.remove(user, session!!)
                call.respond(status = HttpStatusCode.OK, message = mapOf("data" to emptyList<User>()))
            }
            log.info("Response took: ${time.duration}")
        } catch(e: Exception) {
            log.error(e.message)
            call.respond(status = HttpStatusCode.BadRequest, message =  mapOf("error" to e.toString()))
        }
    }
}
