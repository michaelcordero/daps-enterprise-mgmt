package application

import cache.DataCache
import cache.InMemoryCache
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.introspect.VisibilityChecker
import database.LocalDataQuery
import database.queries.DataQuery
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.jwt
import io.ktor.features.*
import io.ktor.freemarker.FreeMarker
import io.ktor.http.CacheControl
import io.ktor.http.ContentType
import io.ktor.http.cio.websocket.DefaultWebSocketSession
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.readText
import io.ktor.http.content.CachingOptions
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
import io.ktor.websocket.WebSockets
import io.ktor.websocket.webSocket
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import model.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import presenters.*
import routes.api.*
import routes.web.*
import security.DAPSJWT
import security.DAPSSecurity
import security.DAPSSession
import server.statuses
import java.time.ZoneId
import java.util.*
import kotlin.collections.LinkedHashSet
import kotlin.time.ExperimentalTime


val log: Logger = LoggerFactory.getLogger(Application::class.java)
val dapsJWT: DAPSJWT = DAPSJWT("secret-jwt")
val dq: DataQuery = LocalDataQuery()
val cache: DataCache = InMemoryCache(dq)
val theme: Theme = Theme.DARK
val host = System.getProperty("host") ?: "localhost"
//NetworkInterface.getNetworkInterfaces()
//.toList().stream()
//.flatMap { i -> i.interfaceAddresses.stream() }
//.filter { ia -> ia.address is Inet4Address && !ia.address.isLoopbackAddress }
//.toList().first().address.hostAddress.toString()
val port = System.getProperty("port") ?: "8080"
//val in_mem_session: SessionStorageMemory = SessionStorageMemory()


@ExperimentalTime
@ExperimentalStdlibApi
@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun main(args: Array<String>) {
    log.info("Program started with args: %s".format(args.joinToString(" ")))
    log.info("Starting server...")
    val server: NettyApplicationEngine = embeddedServer(
        factory = Netty,
        host = host,
        port = port.toInt(),
        watchPaths = listOf("daps-enterprise-mgmt"),
        module = Application::module
    )
    server.start(true)
}

@ExperimentalTime
@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
@Suppress("unused") // Referenced in application.conf
//@JvmOverloads
fun Application.module() {  //testing: Boolean = false
    log.info("application module starting...")
    environment.monitor.subscribe(ApplicationStopped) {
        dq.close()
        // try to figure out how to call the /logout route from here
        //it.locations.href(WebLogin())
        it.dispose()
//        cache.shutdown(true)
    }
    // This feature handles the authentication for Web & HTTP API Requests
    install(Authentication) {
        basic("web") {
            skipWhen { call ->
                try {
                    val session = call.sessions.get<DAPSSession>()
                    dapsJWT.verifier.verify(session?.token)
                    return@skipWhen true
                } catch (e: Exception){
                    return@skipWhen false
                }
            }
        }
        form("form") {
            userParamName = "emailId"
            passwordParamName = "password"
            validate {
                val user: User? = cache.allUsers()
                    .find { user -> user.email == it.name && user.passwordHash == DAPSSecurity.hash(it.password) }
                if (user != null) { // sessions.get<DAPSSession>()?.token != null
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
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            this.setVisibility(
                VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY)
            )
            register(ContentType.Application.Json, JacksonConverter(this))
        }
    }
    // This adds automatically Date and Server headers to each response
    install(DefaultHeaders)
    // This feature enables truly open access across domain boundaries
    install(CORS) {
//        host("localhost:4000") to specify client app
//        anyHost()
//        method(HttpMethod.Options)
//        method(HttpMethod.Get)
//        method(HttpMethod.Post)
//        method(HttpMethod.Put)
//        method(HttpMethod.Delete)
//        method(HttpMethod.Patch)
//        header(HttpHeaders.Authorization)
//        allowCredentials = true
    }
    // This uses use the logger to log every call (request/response)
    install(CallLogging)
    // Automatic '304 Not Modified' Responses
    install(ConditionalHeaders)
    // Supports for Range, Accept-Range and Content-Range headers
    install(PartialContent)
    // cache control
    install(CachingHeaders) {
        options {
//            CachingOptions(CacheControl.NoCache(CacheControl.Visibility.Private))
            when(it.contentType?.withoutParameters()) {
                ContentType.Text.JavaScript -> CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 24 * 60 * 60))
                ContentType.Text.CSS -> CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 24 * 60 * 60))
                ContentType.Text.Html -> CachingOptions(CacheControl.NoCache(CacheControl.Visibility.Private))
                else -> null
            }
        }
    }
    // SESSION cookie
    install(Sessions) {
        cookie<DAPSSession>("DAPS_SESSION_ID") {
            cookie.path = "/"
        }
    }
    install(StatusPages) { statuses(WebStatusPresenter(this)) }
    install(Locations)
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }
    install(WebSockets)
    install(Webjars) {
        path = "/webjars" //defaults to /webjars
        zone = ZoneId.systemDefault() //defaults to ZoneId.systemDefault()
    }
    routing {
        // static content
        static("/static/") {
            // css, javascript & images served here
            resources("static")
        }
        // None authentication
        register(RegisterPresenter())
        index()
        weblogout()
        // Initial web authentication
        authenticate("form") {
            weblogin(WebLoginPresenter())
        }
        // Web authentication
        // comment out to experiment with the node.js app
        authenticate("web") {
            route("/web") {
                clients()
                client_notes()
                daps_staff_messages()
                billings()
                tempnotes()
                temps()
            }
            welcome(WelcomePresenter())
            webbillings(WebBillingsPresenter())
            webclients(WebClientsPresenter())
            webclientnotes(WebClientNotesPresenter())
            webdapsstaffmessages(WebDAPSStaffMessagesPresenter())
            webtempnotes(WebTempNotesPresenter())
            webtemps(WebTempsPresenter())
            web_apex_charts(WebChartsPresenter())
            web_traditional_charts(WebChartsPresenter())
            users()
            // Real Time Update Event via WebSocket
            val connections = Collections.synchronizedSet(LinkedHashSet<DefaultWebSocketSession>())
            webSocket("/update") {
                connections += this
                try {
                    while (true) {
                        when (val frame = incoming.receive()) {
                            is Frame.Text -> {
                                val text = frame.readText()
                                // Send message to all the connections, except the messenger connection.
                                for (conn in connections) {
                                    if (conn != this) {
                                        conn.outgoing.send(Frame.Text(text))
                                    }
                                }
                            }
                        }
                    }
                } catch (e: ClosedReceiveChannelException) {
                    log.info("connection closed. ignore.")
                }
                finally {
                    connections -= this
                }
            }
        }
        // API authentication
        route("/api") {
            login()
        }
        authenticate("api") {
            route("/api") {
                billings()
                client_notes()
                clients()
                daps_staff_messages()
                temps()
                tempnotes()
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


