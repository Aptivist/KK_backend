package com.kk.plugins

import com.kk.routes.gameRouting
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        gameRouting()
    }
}
