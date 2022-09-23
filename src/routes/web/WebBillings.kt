package routes.web


import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import presenters.WebBillingsPresenter

@KtorExperimentalLocationsAPI

@Location("/webbillings")
class WebBillings


@KtorExperimentalLocationsAPI
fun Route.webbillings(presenter: WebBillingsPresenter) {
    get<WebBillings> {
            call.respond(FreeMarkerContent("billings.ftl", mapOf("presenter" to presenter), "billings-e-tag"))
    }
}
