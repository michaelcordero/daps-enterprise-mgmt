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
@Location("/web-apex-charts")
class WebApexCharts

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.web_apex_charts(presenter: WebChartsPresenter) {
    get<WebApexCharts> {
        call.respond(FreeMarkerContent("apex-charts.ftl", mapOf("presenter" to presenter), "apex-charts-e-tag:${LocalDateTime.now()}"))
    }
}
