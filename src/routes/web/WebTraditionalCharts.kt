package routes.web

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import presenters.WebChartsPresenter
import java.time.LocalDateTime

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@Location("/web-traditional-charts")
class WebTraditionalCharts

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.web_traditional_charts(presenter: WebChartsPresenter) {
    get<WebTraditionalCharts> {
        call.respond(
            FreeMarkerContent(
                "traditional-charts.ftl",
                mapOf("presenter" to presenter),
                "traditional-charts-e-tag:${LocalDateTime.now()}"
            )
        )
    }
}



