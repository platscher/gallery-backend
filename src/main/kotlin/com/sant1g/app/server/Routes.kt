package com.sant1g.app.server

import com.sant1g.app.controllers.healthCheck
import com.sant1g.app.controllers.photosRoutes
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.routing.route
import io.ktor.routing.routing

fun Application.routes() {
  routing {
    route("/") {
      healthCheck()
    }
    route("/api") {
      photosRoutes()
    }
  }
}
