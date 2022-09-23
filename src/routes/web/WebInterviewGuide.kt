package routes.web

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import presenters.WebInterviewGuidePresenter
import java.time.LocalDateTime

@KtorExperimentalLocationsAPI

@Location("/webinterviewguide")
class WebInterviewGuide


@KtorExperimentalLocationsAPI
fun Route.webinterviewguide(presenter: WebInterviewGuidePresenter){
    get<WebInterviewGuide>{
        call.respond(
            FreeMarkerContent("interview-guides.ftl", mapOf("presenter" to presenter),
            "web-interview-guide-e-tag:${LocalDateTime.now()}")
        )
    }
}
