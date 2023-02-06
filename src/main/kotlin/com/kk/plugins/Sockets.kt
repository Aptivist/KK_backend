package com.kk.plugins


import io.ktor.serialization.kotlinx.*
import io.ktor.server.websocket.*
import java.time.Duration
import io.ktor.server.application.*
import kotlinx.serialization.json.Json


fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
        contentConverter =  KotlinxWebsocketSerializationConverter(Json{
            ignoreUnknownKeys = true
        })
    }
}
