package com.kk.plugins

import com.kk.routes.hostRouting
import com.kk.routes.playerRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        hostRouting()
        playerRouting()
    }
}
