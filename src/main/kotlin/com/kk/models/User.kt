package com.kk.models

import io.ktor.server.websocket.*

interface User {
    abstract val session: WebSocketServerSession?
}
