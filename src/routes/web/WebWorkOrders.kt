package routes.web

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import presenters.WebWorkOrdersPresenter
import java.time.LocalDateTime


@KtorExperimentalLocationsAPI
@Location("/webworkorders")
class WebWorkOrders

@KtorExperimentalLocationsAPI

fun Route.webworkorders(presenter: WebWorkOrdersPresenter) {
    get<WebWorkOrders> {
        call.respond(
            FreeMarkerContent(
                "work-orders.ftl",
                mapOf("presenter" to presenter),
                "work-orders-e-tag:${LocalDateTime.now()}"
            )
        )
    }
}
