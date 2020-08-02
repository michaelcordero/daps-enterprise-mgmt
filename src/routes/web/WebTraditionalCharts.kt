package routes.web

import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.util.KtorExperimentalAPI
import presenters.WebChartsPresenter

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
                "traditional-charts-e-tag"
            )
        )
    }
}



