package com.kk.routes

import com.kk.controllers.GameRoomController
import com.kk.events.GameEventHost
import com.kk.events.GameEventPlayer
import com.kk.models.CreateGameRequest
import com.kk.models.PlayerUser
import com.kk.models.events.HostEvent
import com.kk.models.events.PlayerEvent
import io.ktor.server.routing.*
import io.ktor.server.websocket.*

fun Route.gameRouting(){


    val gameRoomController = GameRoomController()
    webSocket("/host") {
        val (host, rules) = receiveDeserialized<CreateGameRequest>()
        host.session = this
        gameRoomController.handleGameRoom(hostUser = host, rules = rules)
        while (true){
            val event = receiveDeserialized<HostEvent>().toEvent()
            gameRoomController.onEventHost(event)
        }
    }

    webSocket("/player") {
        val client = receiveDeserialized<PlayerUser>()
        client.session = this
        gameRoomController.handlePlayerConnection(client)

        while (true){
            val event = receiveDeserialized<PlayerEvent>().toEvent()
            gameRoomController.onEventPlayer(event)
        }
    }


}

fun HostEvent.toEvent(): GameEventHost {
   return when(event){
       "START_GAME" -> GameEventHost.OnStartGame(host)
       "START_ROUND" -> GameEventHost.OnStartRound(host)
       "ADD_POINT" -> GameEventHost.AddPoint(playerCodePoint?: "",host.code)
       "NO_POINTS" -> GameEventHost.NoPoints(host)
       //"NEXT_QUESTION" -> GameEventHost.
       else -> throw Exception("Error")
    }
}


fun PlayerEvent.toEvent(): GameEventPlayer {
    return when(event){
        "SHOW_PLAYERS" -> GameEventPlayer.OnShowListPlayers(player)
        "SEND_ANSWER" -> GameEventPlayer.OnSendAnswer(answer)
        else -> throw Exception("Error")
    }
}






