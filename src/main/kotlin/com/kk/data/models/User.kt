package com.kk.data.models

import io.ktor.server.websocket.*

abstract class User{
    abstract val code: String?
    abstract var session: WebSocketServerSession?
}

