package com.kk.plugins


import com.google.gson.Gson
import io.ktor.serialization.*
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.websocket.*
import java.time.Duration


fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
        contentConverter =  GsonWebsocketContentConverter()
    }

}
