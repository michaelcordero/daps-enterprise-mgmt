package routes.web

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import presenters.WebWorkOrdersPresenter
import java.time.LocalDateTime


@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
@Location("/webworkorders")
class WebWorkOrders

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
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
