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
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.http.content.*
import io.ktor.jackson.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.sessions.*
import io.ktor.webjars.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import model.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import presenters.*
import routes.api.*
import routes.web.*
import security.DAPSJWT
import security.DAPSRole
import security.DAPSSecurity
import security.DAPSSession
import server.statuses
import kotlin.collections.set
import kotlin.time.ExperimentalTime


val log: Logger = LoggerFactory.getLogger(Application::class.java)
val dapsJWT: DAPSJWT = DAPSJWT("secret-jwt")
val dq: DataQuery = LocalDataQuery()
val cache: DataCache = InMemoryCache(dq)
val theme: Theme = Theme.LIGHT
val host = System.getProperty("host") ?: "0.0.0.0"
//NetworkInterface.getNetworkInterfaces()
//.toList().stream()
//.flatMap { i -> i.interfaceAddresses.stream() }
//.filter { ia -> ia.address is Inet4Address && !ia.address.isLoopbackAddress }
//.toList().first().address.hostAddress.toString()
val port = System.getProperty("port") ?: "8080"
val connections: MutableMap<String?,DefaultWebSocketServerSession> = mutableMapOf()

@ExperimentalTime
@ExperimentalStdlibApi
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
                    dapsJWT.verifier.verify(session?.sessionId)
                    return@skipWhen true
                } catch (e: Exception){
                    return@skipWhen false
                }
            }
        }
        basic("admin") {
            skipWhen { call ->
                try {
                    val session: DAPSSession? = call.sessions.get<DAPSSession>()
                    val user: User? = cache.users_map().values.find { user -> user.email == session?.emailId }
                    return@skipWhen user?.role == DAPSRole.ADMIN
                } catch (e: Exception){
                    return@skipWhen false
                }
            }
        }
        form("form") {
            userParamName = "emailId"
            passwordParamName = "password"
            validate {
                val user: User? = cache.users_map().values
                    .find { user -> user.email == it.name && user.passwordHash == DAPSSecurity.hash(it.password) }
                if (user != null) {
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
//    install(CORS) {
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
//    }
    // This uses the logger to log every call (request/response)
    install(CallLogging)
    // Automatic '304 Not Modified' Responses
    install(ConditionalHeaders)
    // Supports for Range, Accept-Range and Content-Range headers
    install(PartialContent)
    // cache control
    install(CachingHeaders) {
        options { outgoing ->
            when(outgoing.contentType?.withoutParameters()) {
                ContentType.Text.CSS -> CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 24 * 60 * 60))
                ContentType.Text.JavaScript -> CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 24 * 60 * 60))
                else -> CachingOptions(CacheControl.NoStore(CacheControl.Visibility.Public))
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
    }
    routing {
        // static content
        static("/static/") {
            // css, javascript & images served here
            resources("static")
        }
        // None authentication
        index()
        register(RegisterPresenter())
        webresetpassword(WebResetPasswordPresenter())
        weblogout()
        // Initial web authentication
        authenticate("form") {
            weblogin(WebLoginPresenter())
        }
        // Web authentication
        // comment out to experiment with the node.js app
        authenticate("web") {
            route("/web") {
                billings()
                clients()
                client_notes()
                client_perm_notes()
                daps_staff_messages()
                daps_staff()
                interview_guides()
                paste_errors()
                payments()
                perm_notes()
                perm_req_notes()
                tempnotes()
                temps_available_for_work()
                temps()
                work_order_notes()
                work_orders()
                users()
            }
            welcome(WelcomePresenter())
            webbillings(WebBillingsPresenter())
            webclients(WebClientsPresenter())
            webclientnotes(WebClientNotesPresenter())
            webclientpermnotes(WebClientPermNotesPresenter())
            webdapsstaffmessages(WebDAPSStaffMessagesPresenter())
            webdapsstaff(WebDAPSStaffPresenter())
            webinterviewguide(WebInterviewGuidePresenter())
            webpasteerrors(WebPasteErrorsPresenter())
            webpayments(WebPaymentsPresenter())
            webpermnotes(WebPermNotesPresenter())
            webpermreqnotes(WebPermReqNotesPresenter())
            webtempnotes(WebTempNotesPresenter())
            webtempsavailableforwork(WebTempsAvailableForWorkPresenter())
            webtemps(WebTempsPresenter())
            webworkorders(WebWorkOrdersPresenter())
            webworkordernotes(WebWorkOrderNotesPresenter())
            web_apex_charts(WebChartsPresenter())
            web_traditional_charts(WebChartsPresenter())
            webdocumentation(WebDocumentationPresenter())
            websettings(WebSettingsPresenter())
            authenticate("admin") {
                webusers(WebUsersPresenter())
            }
            // WebSocket only handles adding/removing connections. InMemoryCache is the central location for
            // real time updates of the data, and alert messages, if the data save failed.
            webSocket("/update") {
                val sessionId: String? = this.call.sessions.get<DAPSSession>()?.sessionId
                connections[sessionId] = this
                try {
                    while (true) {
                        when (val frame = incoming.receive()) {
                            is Frame.Text -> {
                                val text = frame.readText()
                                // Send message to all the connections, except the messenger connection.
                                for (conn in connections.values) {
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
                    connections.remove(sessionId)
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
                client_perm_notes()
                daps_staff_messages()
                interview_guides()
                paste_errors()
                payments()
                perm_notes()
                perm_req_notes()
                temps()
                tempnotes()
                temps_available_for_work()
                work_order_notes()
                work_orders()
                users()
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


