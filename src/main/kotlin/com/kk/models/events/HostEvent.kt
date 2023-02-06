package com.kk.models.events

import com.kk.models.HostUser
import kotlinx.serialization.Serializable

@Serializable
data class HostEvent(
    val host: HostUser,
    val event: String,
    val playerCodePoint: String? = null
)
