package com.daps.ent

import com.daps.ent.database.DataPool
import com.daps.ent.database.DataService
import com.daps.ent.database.LocalDatabase
import com.daps.ent.model.User
import com.daps.ent.status.statuses
import com.daps.ent.routes.*
import com.daps.ent.security.DAPSSession
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.*
import io.ktor.features.DefaultHeaders
import io.ktor.features.PartialContent
import io.ktor.features.StatusPages
import io.ktor.freemarker.FreeMarker
import io.ktor.http.HttpHeaders
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.locations.locations
import io.ktor.request.header
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
import io.ktor.util.hex
import io.ktor.webjars.Webjars
import org.jetbrains.exposed.sql.Database
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.URI
import java.time.ZoneId
import java.util.concurrent.TimeUnit
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


val log: Logger = LoggerFactory.getLogger(Application::class.java)
val dp: DataPool = DataPool()
val dao: DataService = LocalDatabase(Database.connect(dp.pool))
@KtorExperimentalAPI
val hash_key = hex("8f74b48eeb9809d995d7b9916") // generated with pwgen 25 1 -A --remove-chars=ghijklmnopqrstuvwxyz
@KtorExperimentalAPI
val hmac_key = SecretKeySpec(hash_key, "HmacSHA1")


@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun main(args: Array<String>) {
    // In production these values will be passed in via command line or system properties (i.e. VM Options).
    log.info("Program started with args: %s".format(args.joinToString(" ")))
    log.info("Starting database...")
    dao.init()
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
    log.info("Application module starting...")
    environment.monitor.subscribe(ApplicationStopped){ dp.pool.close() }
    // This adds automatically Date and Server headers to each response
    install(DefaultHeaders)
    // Supports for Range, Accept-Range and Content-Range headers
    install(PartialContent)
    // SESSION cookie
    install( Sessions ) {
        cookie<DAPSSession>("SESSION") {
            transform(SessionTransportTransformerMessageAuthentication(hash_key))
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
        login(dao)
        register(dao)
        index(dao)
        welcome(dao)
        users(dao)
        table(dao)
    }
}

/**
 * Method that hashes a [password] by using the globally defined secret key [hmac_key].
 */
fun hash(password: String): String {
    val hmac = Mac.getInstance("HmacSHA1")
    hmac.init(hmac_key)
    return hex(hmac.doFinal(password.toByteArray(Charsets.UTF_8)))
}

suspend fun ApplicationCall.redirect(location: Any) {
    val host = request.host() ?: "localhost"
    val port = request.port().let { if (it == 8080) "" else ":$it" }
    val address = host + port
    respondRedirect("http://$address${application.locations.href(location)}")
}

/**
 * Generates a security code using a [hashFunction], a [date], a [user] and an implicit [HttpHeaders.Referrer]
 * to generate tokens to prevent CSRF attacks.
 */
fun ApplicationCall.securityCode(date: Long, user: User, hashFunction: (String) -> String) =
    hashFunction("$date:${user.userId}:${request.host()}:${refererHost()}")

/**
 * Verifies that a code generated from [securityCode] is valid for a [date] and a [user] and an implicit [HttpHeaders.Referrer].
 * It should match the generated [securityCode] and also not be older than two hours.
 * Used to prevent CSRF attacks.
 */
fun ApplicationCall.verifyCode(date: Long, user: User, code: String, hashFunction: (String) -> String) =
    securityCode(date, user, hashFunction) == code
            && (System.currentTimeMillis() - date).let { it > 0 && it < TimeUnit.MILLISECONDS.convert(2, TimeUnit.HOURS) }

/**
 * Obtains the [refererHost] from the [HttpHeaders.Referrer] header, to check it to prevent CSRF attacks
 * from other domains.
 */
fun ApplicationCall.refererHost() = request.header(HttpHeaders.Referrer)?.let { URI.create(it).host }


