package com.kk.data.models

import io.ktor.server.websocket.*
import kotlinx.serialization.Serializable


@Serializable
data class HostUser(
    override var session: WebSocketServerSession?,
    override var code: String? = null
): User()
