package routes.web

import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.util.KtorExperimentalAPI
import presenters.WebInterviewGuidePresenter
import java.time.LocalDateTime

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@Location("/webinterviewguide")
class WebInterviewGuide

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.webinterviewguide(presenter: WebInterviewGuidePresenter){
    get<WebInterviewGuide>{
        call.respond(
            FreeMarkerContent("interview-guides.ftl", mapOf("presenter" to presenter),
            "web-interview-guide-e-tag:${LocalDateTime.now()}")
        )
    }
}
