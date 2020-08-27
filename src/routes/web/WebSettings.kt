package routes.web

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import presenters.WebSettingsPresenter
import java.time.LocalDateTime

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@Location("/websettings")
data class WebSettings(val email: String = "", val error: String = "")

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.websettings(presenter: WebSettingsPresenter) {
    // HTTP GET METHOD
    get<WebSettings> {
        call.respond(FreeMarkerContent("settings.ftl", mapOf("presenter" to presenter),"settings-etag:${LocalDateTime.now()}"))
    }
    // HTTP POST METHOD
    post<WebSettings> {

    }
}
