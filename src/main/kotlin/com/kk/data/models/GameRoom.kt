package com.kk.data.models

data class GameRoom(
    var code: String,
    var host: HostUser,
    val rules: Rules,
    val players: MutableList<PlayerUser> = mutableListOf()
)



data class Rules(
    val maxPlayers: Int,
    val points: Int,
    val timerSeconds: Int
)
