package routes.web

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
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
