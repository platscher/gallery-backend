package com.sant1g.app.controllers

import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get

fun Route.healthCheck() {
  get("") {
    call.respond(response)
  }
}

val response = mapOf("status" to "ok")
