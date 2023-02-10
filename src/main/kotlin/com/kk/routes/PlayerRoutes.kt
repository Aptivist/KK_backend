package com.kk.routes

import com.kk.controllers.PlayerController
import com.kk.controllers.events.GameEventPlayer
import com.kk.controllers.events.mapper.toEvent
import com.kk.data.models.PlayerUser
import com.kk.data.models.events.PlayerEvent
import com.kk.data.models.toBaseResult
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.koin.ktor.ext.inject
import java.util.*
import kotlin.random.Random


fun Route.playerRouting(){
    val playerController by inject<PlayerController>()
    webSocket("/player") {
        val player = receiveDeserialized<PlayerUser>()
        player.session = this
        player.code = generateRandomCode()
        playerController.handlePlayerConnection(player)
        sendSerialized(player.toBaseResult("CONNECTED"))

        while (true){
            val event = receiveDeserialized<PlayerEvent>().toEvent()
            playerController.onEventPlayer(event)
        }
    }
}

private fun generateRandomCode(): String {
    val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return (1..8)
        .map { _ -> Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")
        .uppercase(Locale.getDefault())
}


