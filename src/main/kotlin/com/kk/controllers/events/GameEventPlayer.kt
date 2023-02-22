package com.kk.controllers.events

import com.kk.data.models.PlayerUser
import com.kk.data.models.events.Answer


sealed interface GameEventPlayer {
    data class OnShowListPlayers(val player: PlayerUser) : GameEventPlayer
    data class OnSendAnswer(val player: PlayerUser,val answer: Answer?) : GameEventPlayer
    data class OnRetryJoinRoom(val player: PlayerUser, val code:String) : GameEventPlayer

}
