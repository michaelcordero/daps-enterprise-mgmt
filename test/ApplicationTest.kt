package com.daps.ent

import com.daps.ent.routes.Index
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.locations
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import io.ktor.util.KtorExperimentalAPI
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ApplicationTest {
//    @KtorExperimentalAPI
//    @KtorExperimentalLocationsAPI
//    @Test
//    fun testIndex() {
//        withTestApplication({ module() }) {
//            // https://ktor.io/servers/features/locations.html#building-urls
//            val path: String = application.locations.href(Index())
//            handleRequest(HttpMethod.Get, path).apply {
//                assertEquals(HttpStatusCode.OK, response.status())
//                assertTrue(response.content!!.contains("Hello Ktor!"))
//            }
//        }
//    }
}
