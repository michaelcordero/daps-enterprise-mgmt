package routes.web


import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.util.KtorExperimentalAPI
import presenters.WebBillingsPresenter
import security.DAPSSession

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@Location("/webbillings")
class WebBillings

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.webbillings(presenter: WebBillingsPresenter){
    get<WebBillings>{
        val session: DAPSSession? = call.sessions.get<DAPSSession>()
        if (session != null) {
            call.respond(FreeMarkerContent("billings.ftl", mapOf("presenter" to presenter),"billings-e-tag"))
        } else {
            call.respond(FreeMarkerContent("weblogin.ftl", mapOf("user" to "null"), "web-login-e-tag"))
        }
    }
}
