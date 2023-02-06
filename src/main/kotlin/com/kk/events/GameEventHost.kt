package com.kk.events

import com.kk.models.HostUser

sealed interface GameEventHost{
    data class OnStartGame(val hostUser: HostUser): GameEventHost
    data class OnStartRound(val hostUser: HostUser): GameEventHost
    data class AddPoint(val playerIdPoint: String, val code: String): GameEventHost
    data class NoPoints(val hostUser: HostUser): GameEventHost

}
