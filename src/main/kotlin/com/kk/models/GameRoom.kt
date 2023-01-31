package com.kk.models

data class GameRoom(
    var code: String,
    var host: HostUser,
    var isInitialized: Boolean = false,
    val users: MutableList<PlayerUser> = mutableListOf()
)
