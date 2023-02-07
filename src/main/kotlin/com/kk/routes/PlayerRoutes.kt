package com.kk.routes

import com.kk.controllers.PlayerController
import com.kk.controllers.events.GameEventPlayer
import com.kk.controllers.events.mapper.toEvent
import com.kk.data.models.PlayerUser
import com.kk.data.models.events.PlayerEvent
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.koin.ktor.ext.inject


fun Route.playerRouting(){
    val playerController by inject<PlayerController>()
    webSocket("/player") {
        val client = receiveDeserialized<PlayerUser>()
        client.session = this
        playerController.handlePlayerConnection(client)

        while (true){
            val event = receiveDeserialized<PlayerEvent>().toEvent()
            playerController.onEventPlayer(event)
        }
    }
}


