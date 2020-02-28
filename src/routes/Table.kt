package com.daps.ent.routes

import com.daps.ent.facades.DataService
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.routing.Route

@KtorExperimentalLocationsAPI
@Location("/table/{id}")
data class Table(val id: String )

@KtorExperimentalLocationsAPI
fun Route.table(dao: DataService) {
    get<Table> { id -> }
}
