package application

import com.fasterxml.jackson.databind.SerializationFeature
import database.LocalDataQuery
import database.queries.DataQuery
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.jwt
import io.ktor.features.*
import io.ktor.freemarker.FreeMarker
import io.ktor.http.ContentType
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.jackson.JacksonConverter
import io.ktor.jackson.jackson
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.locations.locations
import io.ktor.request.host
import io.ktor.request.port
import io.ktor.response.respondRedirect
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.util.KtorExperimentalAPI
import io.ktor.webjars.Webjars
import model.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import presenters.RegisterPresenter
import presenters.WebLoginPresenter
import presenters.WelcomePresenter
import routes.api.billings
import routes.api.clientNotes
import routes.api.clients
import routes.api.login
import routes.web.*
import security.DAPSJWT
import security.DAPSSecurity
import security.DAPSSession
import server.statuses
import java.text.DateFormat
import java.time.ZoneId


val log: Logger = LoggerFactory.getLogger(Application::class.java)
val dq: DataQuery = LocalDataQuery()
val dapsJWT: DAPSJWT = DAPSJWT("secret-jwt")


@ExperimentalStdlibApi
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
        dq.close()
        // try to figure out how to call the /logout route from here
        //it.locations.href(WebLogin())
        it.dispose()
    }
    // This feature handles the authentication for Web & HTTP API Requests
    install(Authentication){
        basic ("web"){
            skipWhen { call ->
                call.sessions.get<DAPSSession>()?.token != null
            }
        }
        form("form") {
            userParamName = "emailId"
            passwordParamName = "password"
            validate {
                val user: User? = dq.user(it.name, DAPSSecurity.hash(it.password))
                if ( user != null) { // sessions.get<DAPSSession>()?.token != null
                    UserIdPrincipal(it.name)
                } else {
                    null
                }
            }
        }
        jwt("api") {
            verifier(dapsJWT.verifier)
            validate {
                UserIdPrincipal(it.payload.getClaim("name").asString())
            }
        }
    }
    // This feature enables the HTTP API to respond with JSON Content
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
            dateFormat = DateFormat.getDateInstance()
            register(ContentType.Application.Json, JacksonConverter())
        }
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
            cookie.path = "/"
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
        // None authentication
        register(RegisterPresenter(dq, dapsJWT))
        index()
        weblogout()
        // Initial web authentication
        authenticate("form") {
            weblogin(WebLoginPresenter(dq, dapsJWT))
        }
        // Web authentication
        authenticate("web") {
            welcome(WelcomePresenter(dq))
            users(dq)
            table(dq)
        }
        // API authentication
        route("/api") {
            login(dq, dapsJWT)
        }
        authenticate("api") {
            route("/api") {
                clients(dq)
                billings(dq)
                clientNotes(dq)
            }
        }
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


