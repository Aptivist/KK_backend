package com.kk.models.events

import com.kk.models.PlayerUser
import com.kk.routes.Answer
import kotlinx.serialization.Serializable


@Serializable
data class PlayerEvent(
    val player: PlayerUser,
    val event: String,
    val answer: Answer? = null
)
