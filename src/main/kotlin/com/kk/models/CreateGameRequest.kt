package com.kk.models

import kotlinx.serialization.Serializable

@Serializable
data class CreateGameRequest(
    val host: HostUser,
    val rules: Rules,
)
