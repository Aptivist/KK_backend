package com.kk.data.models.events

import com.kk.data.models.PlayerUser




data class PlayerEvent(
    val player: PlayerUser,
    val event: String,
    val answer: Answer? = null
)
