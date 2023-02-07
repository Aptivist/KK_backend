package com.kk.data.models

import kotlinx.serialization.Serializable

@Serializable
data class CreateGameRequest(
    val host: HostUser,
    val rules: Rules,
)
