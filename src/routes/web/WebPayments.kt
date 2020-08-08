package routes.web

import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.util.KtorExperimentalAPI
import presenters.WebPaymentsPresenter
import java.time.LocalDateTime

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@Location("/webpayments")
class WebPayments

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.webpayments(presenter: WebPaymentsPresenter){
    get<WebPayments>{
        call.respond(FreeMarkerContent("payments.ftl", mapOf("presenter" to presenter),
        "payments-e-tag:${LocalDateTime.now()}"))
    }
}
