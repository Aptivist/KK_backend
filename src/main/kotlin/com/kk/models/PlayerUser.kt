package com.kk.models

import io.ktor.server.websocket.*

data class PlayerUser(
    val name: String,
    override val session: WebSocketServerSession?,
    ): User
