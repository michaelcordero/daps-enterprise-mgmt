package routes.web


import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import presenters.WebBillingsPresenter
import java.time.LocalDateTime

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@Location("/webbillings")
class WebBillings

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.webbillings(presenter: WebBillingsPresenter) {
    get<WebBillings> {
            call.respond(FreeMarkerContent("billings.ftl", mapOf("presenter" to presenter), "billings-e-tag:${LocalDateTime.now()}"))
    }
}
