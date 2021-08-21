package routes.web

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import presenters.WebPaymentsPresenter
import java.time.LocalDateTime

@KtorExperimentalLocationsAPI

@Location("/webpayments")
class WebPayments


@KtorExperimentalLocationsAPI
fun Route.webpayments(presenter: WebPaymentsPresenter){
    get<WebPayments>{
        call.respond(FreeMarkerContent("payments.ftl", mapOf("presenter" to presenter),
        "payments-e-tag:${LocalDateTime.now()}"))
    }
}
