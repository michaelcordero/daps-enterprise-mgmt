package routes

import database.queries.DataQuery
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.routing.Route

@KtorExperimentalLocationsAPI
@Location("/table/{id}")
data class Table(val id: String )

@KtorExperimentalLocationsAPI
fun Route.table(dao: DataQuery) {
    get<Table> { id -> }
}
