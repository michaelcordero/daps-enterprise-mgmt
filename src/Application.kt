package com.daps.ent

import com.daps.ent.database.DataPool
import com.daps.ent.database.DataService
import com.daps.ent.database.LocalDatabase
import com.daps.ent.status.statuses
import com.daps.ent.routes.*
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.Application
import io.ktor.application.ApplicationStopped
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.features.StatusPages
import io.ktor.freemarker.FreeMarker
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import io.ktor.util.KtorExperimentalAPI
import io.ktor.webjars.Webjars
import org.jetbrains.exposed.sql.Database
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.ZoneId


val log: Logger = LoggerFactory.getLogger(Application::class.java)
val dp: DataPool = DataPool()
val dao: DataService = LocalDatabase(Database.connect(dp.pool))


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
        index(dao)
        welcome(dao)
        users(dao)
        table(dao)
    }
}

