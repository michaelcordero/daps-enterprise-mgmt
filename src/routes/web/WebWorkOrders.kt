package routes.web

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
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
