package com.kk.data.models

import io.ktor.server.websocket.*
import kotlinx.serialization.Serializable

@Serializable
data class PlayerUser(
    val id: String,
    val name: String,
    var points: Int? = null,
    override var session: WebSocketServerSession?= null, override val code: String,
    ): User()
