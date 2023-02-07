package com.kk.controllers.events

import com.kk.data.models.HostUser

sealed interface GameEventHost{
    data class OnStartRound(val hostUser: HostUser): GameEventHost
    data class AddPoint(val playerIdPoint: String, val host: HostUser): GameEventHost
    data class NoPoints(val hostUser: HostUser): GameEventHost

}
