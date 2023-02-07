package com.kk.controllers.events.mapper

import com.kk.controllers.events.GameEventPlayer
import com.kk.data.models.events.PlayerEvent

fun PlayerEvent.toEvent(): GameEventPlayer {
    return when(event){
        "SHOW_PLAYERS" -> GameEventPlayer.OnShowListPlayers(player)
        "SEND_ANSWER" -> GameEventPlayer.OnSendAnswer(answer)
        else -> throw Exception("Error")
    }
}