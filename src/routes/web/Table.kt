package routes.web

import database.queries.DataQuery
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route

@KtorExperimentalLocationsAPI
@Location("/table")
class Table

@KtorExperimentalLocationsAPI
fun Route.table(dq: DataQuery) {
    get<Table> {
        call.respond(FreeMarkerContent("table.ftl", mapOf("test" to "nothing"), "someeetag"))
    }
}
