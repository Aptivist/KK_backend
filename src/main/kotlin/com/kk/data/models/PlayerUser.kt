package com.kk.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.ktor.server.websocket.*


data class PlayerUser(
    val id: String,
    val name: String,
    var points: Int? = null,
    @Transient
    override var session: WebSocketServerSession?= null,
    override val code: String,
    ): User()
