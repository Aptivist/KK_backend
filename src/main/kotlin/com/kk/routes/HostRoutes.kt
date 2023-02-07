package com.kk.routes

import com.kk.controllers.HostController
import com.kk.controllers.events.mapper.toEvent
import com.kk.data.models.CreateGameRequest
import com.kk.data.models.events.HostEvent
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.koin.ktor.ext.inject


fun Route.hostRouting(){

    val hostController by inject<HostController> ()
    webSocket("/host") {
        val (host, rules) = receiveDeserialized<CreateGameRequest>()
        host.session = this
        hostController.handleGameRoom(hostUser = host, rules = rules)
        while (true){
            val event = receiveDeserialized<HostEvent>().toEvent()
            hostController.onEventHost(event)
        }
    }
}


