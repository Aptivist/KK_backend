package com.kk.routes

import com.kk.controllers.HostController
import com.kk.controllers.events.mapper.toEvent
import com.kk.data.models.CreateGameRequest
import com.kk.data.models.HostUser
import com.kk.data.models.events.HostEvent
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import org.koin.ktor.ext.inject


fun Route.hostRouting(){

    val hostController by inject<HostController> ()
    webSocket("/host") {
        val (rules) = receiveDeserialized<CreateGameRequest>()
        val host = HostUser(this, code = "")
        hostController.handleGameRoom(hostUser = host, rules = rules)
        try {
            while (true){
                val event = receiveDeserialized<HostEvent>().toEvent(host)
                hostController.onEventHost(event)
            }
        }catch (e: Exception){
            host.session.close()
            hostController.removePlayerSession(hostA)
        }

    }
}


