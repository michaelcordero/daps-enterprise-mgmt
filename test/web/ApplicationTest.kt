package com.daps.ent.web

import io.ktor.server.locations.*
import org.junit.Test
import kotlin.time.ExperimentalTime

class ApplicationTest {
    @ExperimentalTime

    @KtorExperimentalLocationsAPI
    @Test
    fun testIndex() {
//        withTestApplication({ module() }) {
//            // https://ktor.io/servers/features/locations.html#building-urls
//            val path: String = application.locations.href(Index())
//            handleRequest(HttpMethod.Get, path).apply {
//                assertEquals(HttpStatusCode.OK, response.status())
//                // verifies redirect when no user is logged in
//                assertTrue { response.content!!.contains("Sign In") }
//            }
        }

//
//    @KtorExperimentalLocationsAPI
//    @Test
//    fun testClients() {
//        withTestApplication({ module() }) {
//            val path: String = application.locations.href(Clients())
//            handleRequest(HttpMethod.Get, path).apply {
//                assertEquals(response.contentType().contentType, ContentType.Application.Json.contentType)
//            }
//        }
//    }
}
