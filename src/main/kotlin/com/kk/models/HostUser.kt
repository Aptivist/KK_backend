package com.kk.models

import io.ktor.server.websocket.*

data class HostUser(
    override val session: WebSocketServerSession?
): User()
