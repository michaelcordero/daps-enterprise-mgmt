package routes.web

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import presenters.WebPaymentsPresenter
import java.time.LocalDateTime

@KtorExperimentalLocationsAPI

@Location("/webpayments")
class WebPayments


@KtorExperimentalLocationsAPI
fun Route.webpayments(presenter: WebPaymentsPresenter){
    get<WebPayments>{
        call.respond(
            FreeMarkerContent("payments.ftl", mapOf("presenter" to presenter),
        "payments-e-tag:${LocalDateTime.now()}")
        )
    }
}
