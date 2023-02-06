package com.kk.events

import com.kk.models.PlayerUser
import com.kk.models.events.Answer


sealed interface GameEventPlayer {
    data class OnShowListPlayers(val playerUser: PlayerUser) : GameEventPlayer
    data class OnSendAnswer(val answer: Answer?) : GameEventPlayer

}
