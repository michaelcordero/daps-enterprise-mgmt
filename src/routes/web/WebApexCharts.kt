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
@Location("/web-apex-charts")
class WebApexCharts

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.web_apex_charts(presenter: WebChartsPresenter) {
    get<WebApexCharts> {
        call.respond(FreeMarkerContent("apex-charts.ftl", mapOf("presenter" to presenter), "apex-charts-e-tag"))
    }
}
