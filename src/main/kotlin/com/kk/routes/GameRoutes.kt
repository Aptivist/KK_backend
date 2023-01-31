package com.kk.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.gameRouting(){
    get("/") {
        call.respondText("Hello World!")
    }
}