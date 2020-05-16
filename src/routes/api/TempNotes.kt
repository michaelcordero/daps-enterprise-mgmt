package routes.api

import application.log
import cache.InMemoryCache
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalLocationsAPI
@Location("/tempnotes")
class TempNotes

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.tempnotes(cache: InMemoryCache) {
    get<TempNotes> {
        try {
            log.info("/tempnotes requested")
            call.respond(status = HttpStatusCode.OK, message = cache.tempNotes)
        } catch (e: Exception) {
            call.respond(status = HttpStatusCode.BadRequest, message = "Bad Request: $e")
        }
    }
}
