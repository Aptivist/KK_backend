package com.kk.data.models.events

import com.kk.data.models.HostUser
import kotlinx.serialization.Serializable

@Serializable
data class HostEvent(
    val host: HostUser,
    val event: String,
    val playerCodePoint: String? = null
)