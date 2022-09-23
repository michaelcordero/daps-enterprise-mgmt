package routes.web


import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
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
