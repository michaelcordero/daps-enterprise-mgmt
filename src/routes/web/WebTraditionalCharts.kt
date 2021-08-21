package routes.web

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
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



