package routes.web

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
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
