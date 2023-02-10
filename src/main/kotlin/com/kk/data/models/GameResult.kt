package com.kk.data.models

data class GameResult(
    val listPlayers: List<PlayerUser>? = emptyList(),
    val roundPlayerWon: PlayerUser? = null
)
