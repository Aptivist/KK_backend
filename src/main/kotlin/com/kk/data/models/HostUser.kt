package com.kk.data.models

import io.ktor.server.websocket.*


data class HostUser(
    @Transient
    override var session: WebSocketServerSession,
    override var code: String
): User()
