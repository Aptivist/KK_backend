package com.kk.controllers


import com.kk.controllers.events.GameEventPlayer
import com.kk.data.AnswerDataSource
import com.kk.data.GameRoomDataSource
import com.kk.data.models.BaseResult
import com.kk.data.models.PlayerUser
import com.kk.data.models.RoomConnectionStatus
import com.kk.data.models.events.Answer
import com.kk.data.models.toBaseResult
import io.ktor.server.websocket.*


class PlayerController(
    private val gameRoomDataSource: GameRoomDataSource,
    private val answerDataSource: AnswerDataSource
) {
    private fun setPlayerConnection(playerUser: PlayerUser) : RoomConnectionStatus {
        val currentRoom = gameRoomDataSource.getRoomByCode(playerUser.code)
        currentRoom?.let {
            if (it.players.size + 1 <= it.rules.maxPlayers){
                it.players.add(playerUser)
                return RoomConnectionStatus.Success()
            } else {
                return RoomConnectionStatus.Error("EXCEEDED_MAXIMUM_PLAYERS")
            }
        } ?: return RoomConnectionStatus.Error("SESSION_CODE_NOT_VALID")

    }

    suspend fun onEventPlayer(event: GameEventPlayer) {
        when (event) {
            is GameEventPlayer.OnSendAnswer -> {
                sendAnswer(event.player, event.answer)
            }

            is GameEventPlayer.OnShowListPlayers -> {
                showPlayers(event.player.code)
            }

            is GameEventPlayer.OnRetryJoinRoom -> {
                tryJoinRoom(event.player)
            }
        }
    }

    suspend fun tryJoinRoom(playerUser: PlayerUser) {
        when(val result = setPlayerConnection(playerUser)){
            is RoomConnectionStatus.Error -> playerUser.session.sendSerialized(BaseResult(result.message, null))
            is RoomConnectionStatus.Success -> playerUser.session.sendSerialized(playerUser.toBaseResult(result.message))

        }
    }

    private fun sendAnswer(playerUser: PlayerUser, answer: Answer?) {
        answer?.playerId = playerUser.id
        answer?.let { answerDataSource.addAnswer(it) }
    }

    private suspend fun showPlayers(code: String) {
        val currentRoom = gameRoomDataSource.getRoomByCode(code)
        val players = currentRoom?.players
        currentRoom?.host?.session?.sendSerialized(players.toBaseResult("WAITING"))
        players?.forEach {
            it.session.sendSerialized(players.toBaseResult("WAITING"))
        }
    }

    fun removeFromGameRoom(playerUser: PlayerUser){
        val currentRoom = gameRoomDataSource.getRoomByCode(playerUser.code) ?: return
        currentRoom.players.removeIf { it.id == playerUser.id }
    }


}