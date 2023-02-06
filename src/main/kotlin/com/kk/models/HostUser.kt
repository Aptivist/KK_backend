package com.kk.models

import io.ktor.server.websocket.*
import kotlinx.serialization.Serializable


@Serializable
data class HostUser(
    override var session: WebSocketServerSession?,
    override val code: String
): User()
