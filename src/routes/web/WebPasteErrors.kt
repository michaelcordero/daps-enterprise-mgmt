package routes.web

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import presenters.WebPasteErrorsPresenter
import java.time.LocalDateTime


@KtorExperimentalLocationsAPI
@Location("/webpasteerrors")
class WebPasteErrors

@KtorExperimentalLocationsAPI

fun Route.webpasteerrors(presenter: WebPasteErrorsPresenter) {
    get<WebPasteErrors> {
        call.respond(
            FreeMarkerContent(
                "paste-errors.ftl",
                mapOf("presenter" to presenter),
                "paste-errors-e-tag:${LocalDateTime.now()}"
            )
        )
    }

}
