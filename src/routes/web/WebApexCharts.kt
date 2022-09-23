package routes.web

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import presenters.WebChartsPresenter

@KtorExperimentalLocationsAPI

@Location("/web-apex-charts")
class WebApexCharts


@KtorExperimentalLocationsAPI
fun Route.web_apex_charts(presenter: WebChartsPresenter) {
    get<WebApexCharts> {
        call.respond(FreeMarkerContent("apex-charts.ftl", mapOf("presenter" to presenter), "apex-charts-e-tag"))
    }
}
