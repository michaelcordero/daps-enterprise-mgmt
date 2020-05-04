package com.daps.ent.web

import application.module
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.locations
import io.ktor.server.testing.contentType
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import io.ktor.util.KtorExperimentalAPI
import org.junit.Test
import routes.api.Clients
import routes.web.Index
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ApplicationTest {
    @KtorExperimentalAPI
    @KtorExperimentalLocationsAPI
    @Test
    fun testIndex() {
        withTestApplication({ module() }) {
            // https://ktor.io/servers/features/locations.html#building-urls
            val path: String = application.locations.href(Index())
            handleRequest(HttpMethod.Get, path).apply {
                assertEquals(HttpStatusCode.OK, response.status())
                // verifies redirect when no user is logged in
                assertTrue { response.content!!.contains("Sign In") }
            }
        }
    }

    @KtorExperimentalAPI
    @KtorExperimentalLocationsAPI
    @Test
    fun testClients() {
        withTestApplication({ module() }) {
            val path: String = application.locations.href(Clients())
            handleRequest(HttpMethod.Get, path).apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(response.contentType().contentType, ContentType.Application.Json.contentType)
            }
        }
    }
}
