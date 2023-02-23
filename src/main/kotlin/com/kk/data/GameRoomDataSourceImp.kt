package com.kk.data

import com.kk.data.models.GameRoom
import com.kk.data.models.events.Answer
import java.util.*

class GameRoomDataSourceImp: GameRoomDataSource {

    private val gameRooms = Collections.synchronizedSet<GameRoom>(LinkedHashSet())

    override fun addRoom(gameRoom: GameRoom) {
        gameRooms.add(gameRoom)
    }

    override fun getRoomByCode(code: String): GameRoom? {
        return gameRooms.find { it.code == code }
    }

    override fun updateStatusByCode(code: String, isInitialized: Boolean) {
         gameRooms.find { it.code == code }?.isInitialized = isInitialized
    }

    override fun removeRoom(gameRoom: GameRoom) {
        gameRooms.remove(gameRoom)
    }
}