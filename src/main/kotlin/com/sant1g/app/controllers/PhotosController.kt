package com.sant1g.app.controllers

import com.sant1g.app.services.PhotoService
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.photosRoutes() {
    val photoService by inject<PhotoService>()

    route("/v1/photos") {
        get("/single") {
            call.respond(photoService.getSinglePhoto())
        }
    }
}
