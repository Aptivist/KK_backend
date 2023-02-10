package com.kk.data.models


data class CreateGameRequest(
    val host: HostUser,
    val rules: Rules,
)
