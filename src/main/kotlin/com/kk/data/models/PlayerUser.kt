package com.kk.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.ktor.server.websocket.*


data class PlayerUser(
    var id: String? = null,
    val name: String,
    var points: Int = 0,
    @Transient
    override var session: WebSocketServerSession,
    override var code: String,
    ): User()
