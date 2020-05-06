package routes.web

import database.queries.DataQuery
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.routing.Route

@KtorExperimentalLocationsAPI
@Location("/table")
class Table

@KtorExperimentalLocationsAPI
fun Route.table(dq: DataQuery) {
    get<Table> {}
}
