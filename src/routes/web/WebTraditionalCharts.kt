package routes.web

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import presenters.WebChartsPresenter

@KtorExperimentalLocationsAPI

@Location("/web-traditional-charts")
class WebTraditionalCharts


@KtorExperimentalLocationsAPI
fun Route.web_traditional_charts(presenter: WebChartsPresenter) {
    get<WebTraditionalCharts> {
        call.respond(
            FreeMarkerContent(
                "traditional-charts.ftl",
                mapOf("presenter" to presenter),
                "traditional-charts-e-tag"
            )
        )
    }
}



