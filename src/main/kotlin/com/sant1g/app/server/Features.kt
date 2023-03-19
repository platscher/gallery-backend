package com.sant1g.app.server

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.sant1g.app.configuration.Environment
import com.sant1g.app.metrics.KtorMicrometer
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.metrics.micrometer.MicrometerMetrics
import io.ktor.routing.Routing

fun Application.features() {
  install(DefaultHeaders)
  install(Compression)
  if (Environment.isDevelopment()) {
    install(CallLogging)
  }
  install(StatusPages)

  install(ContentNegotiation) {
    jackson {
      propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
    }
  }

  if (Environment.isDevelopment().not()) {
    install(MicrometerMetrics) {
      registry = KtorMicrometer.getRegistry()
      meterBinders = KtorMicrometer.meterBinders()
    }
  }
  install(Routing) {
  }
}
