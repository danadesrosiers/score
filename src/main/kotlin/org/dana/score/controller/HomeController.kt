package org.dana.score.controller

import org.jetbrains.ktor.application.call
import org.jetbrains.ktor.content.resolveResource
import org.jetbrains.ktor.routing.Route
import org.jetbrains.ktor.routing.get
import org.jetbrains.ktor.routing.route


fun Route.home() {
    route("/") {
        get("/") {
            // TODO: Build Home Page
        }
        get("/js/{jsFile}") {
            call.respond(call.resolveResource("js/${call.parameters["jsFile"]}")!!)
        }
    }
}