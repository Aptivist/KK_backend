package com.kk.data

import com.kk.data.models.GameRoom
import com.kk.data.models.events.Answer
import java.util.*

interface GameRoomDataSource {
    fun addRoom(gameRoom: GameRoom)
    fun getRoomByCode(code: String): GameRoom?
    fun removeRoom(gameRoom: GameRoom)
}