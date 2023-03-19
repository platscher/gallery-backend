package com.sant1g.app

import com.sant1g.app.server.routes
import io.ktor.application.Application
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class HealthCheckControllerTest {

  @Test
  fun testHealthCheck() {
    withTestApplication(Application::routes) {
      handleRequest(HttpMethod.Get, "/").apply {
        assertEquals(HttpStatusCode.OK, response.status())
      }
    }
  }
}
