package com.kk.data.models

import io.ktor.server.websocket.*
import kotlinx.serialization.Serializable

@Serializable
abstract class User{
    abstract val code: String?
    abstract var session: WebSocketServerSession?
}

