package application

import database.LocalDataService
import database.queries.DataService
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.freemarker.FreeMarker
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.locations.locations
import io.ktor.request.host
import io.ktor.request.port
import io.ktor.response.respondRedirect
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import io.ktor.sessions.SessionTransportTransformerMessageAuthentication
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import io.ktor.util.KtorExperimentalAPI
import io.ktor.webjars.Webjars
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import presenters.LoginPresenter
import presenters.RegisterPresenter
import presenters.WelcomePresenter
import routes.*
import security.DAPSSecurity
import security.DAPSSession
import server.statuses
import java.time.ZoneId


val log: Logger = LoggerFactory.getLogger(Application::class.java)
val dao: DataService = LocalDataService()


@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun main(args: Array<String>) {
    // In production these values will be passed in via command line or system properties (i.e. VM Options).
    log.info("Program started with args: %s".format(args.joinToString(" ")))
    log.info("Starting database...")
    log.info("Starting server...")
    val server: NettyApplicationEngine = embeddedServer(
        factory = Netty,
        host = "localhost",
        port = 8080,
        watchPaths = listOf("daps-enterprise-mgmt"),
        module = Application::module
    )
    server.start(true)
}

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
@Suppress("unused") // Referenced in application.conf
//@JvmOverloads
fun Application.module() {  //testing: Boolean = false
    log.info("application module starting...")
    environment.monitor.subscribe(ApplicationStopped){
        dao.close()
        // try to figure out how to call the /logout route from here
        //it.locations.href(Login())
        it.dispose()
    }
    // This adds automatically Date and Server headers to each response
    install(DefaultHeaders)
    // This uses use the logger to log every call (request/response)
    install(CallLogging)
    // Automatic '304 Not Modified' Responses
    install(ConditionalHeaders)
    // Supports for Range, Accept-Range and Content-Range headers
    install(PartialContent)
    // SESSION cookie
    install( Sessions ) {
        cookie<DAPSSession>("SESSION") {
            transform(SessionTransportTransformerMessageAuthentication(DAPSSecurity.hash_key))
        }
    }
    install(StatusPages) { statuses(this) }
    install(Locations)
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }
    install( Webjars ) {
        path = "/webjars" //defaults to /webjars
        zone = ZoneId.systemDefault() //defaults to ZoneId.systemDefault()
    }
    routing {
        // static content
        static ("/static/" ) {
            // css, javascript & images served here
            resources("static")
        }
        // pages
        login(LoginPresenter(dao))
        register(RegisterPresenter(dao))
        index(dao)
        welcome(WelcomePresenter(dao))
        users(dao)
        table(dao)
    }
}

@KtorExperimentalLocationsAPI
suspend fun ApplicationCall.redirect(location: Any) {
    val host = request.host()
    val port = request.port().let { if (it == 8080) ":8080" else ":$it" }
    val address = host + port
    respondRedirect("http://$address${application.locations.href(location)}")
}

///**
// * Generates a security code using a [hashFunction], a [date], a [user] and an implicit [HttpHeaders.Referrer]
// * to generate tokens to prevent CSRF attacks.
// */
//fun ApplicationCall.securityCode(date: Long, user: User, hashFunction: (String) -> String) =
//    hashFunction("$date:${user.email}:${request.host()}:${refererHost()}")

///**
// * Verifies that a code generated from [securityCode] is valid for a [date] and a [user] and an implicit [HttpHeaders.Referrer].
// * It should match the generated [securityCode] and also not be older than two hours.
// * Used to prevent CSRF attacks.
// */
//fun ApplicationCall.verifyCode(date: Long, user: User, code: String, hashFunction: (String) -> String) =
//    securityCode(date, user, hashFunction) == code
//            && (System.currentTimeMillis() - date).let { it > 0 && it < TimeUnit.MILLISECONDS.convert(2, TimeUnit.HOURS) }

///**
// * Obtains the [refererHost] from the [HttpHeaders.Referrer] header, to check it to prevent CSRF attacks
// * from other domains.
// */
//fun ApplicationCall.refererHost() = request.header(HttpHeaders.Referrer)?.let { URI.create(it).host }


