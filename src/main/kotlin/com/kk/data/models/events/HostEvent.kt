package com.kk.data.models.events

import com.kk.data.models.HostUser

data class HostEvent(
    val event: String,
    val playerIdPoint: String? = null
)
