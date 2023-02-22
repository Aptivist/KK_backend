package com.kk.controllers.events.mapper

import com.kk.controllers.events.GameEventPlayer
import com.kk.data.models.PlayerUser
import com.kk.data.models.events.PlayerEvent

fun PlayerEvent.toEvent(playerUser: PlayerUser): GameEventPlayer {
    return when(event){
        "SHOW_PLAYERS" -> GameEventPlayer.OnShowListPlayers(playerUser)
        "SEND_ANSWER" -> GameEventPlayer.OnSendAnswer(playerUser, answer)
        "TRY_JOIN_ROOM" -> GameEventPlayer.OnRetryJoinRoom(playerUser, code)
        else -> throw Exception("Error")
    }
}