package com.kk.data.models

import kotlinx.serialization.Serializable

@Serializable
data class GameRoom(
    var code: String,
    var host: HostUser,
    var isInitialized: Boolean = false,
    val rules: Rules,
    val players: MutableList<PlayerUser> = mutableListOf()
)


@Serializable
data class Rules(
    val maxPlayers: Int,
    val points: Int,
    val timerSeconds: Int
)
