package com.kk.controllers


import com.kk.controllers.events.GameEventPlayer
import com.kk.data.AnswerDataSource
import com.kk.data.GameRoomDataSource
import com.kk.data.models.PlayerUser
import com.kk.data.models.events.Answer
import com.kk.data.models.toBaseResult
import io.ktor.server.websocket.*


class PlayerController(
    private val gameRoomDataSource: GameRoomDataSource,
    private val answerDataSource: AnswerDataSource
) {
    fun handlePlayerConnection(playerUser: PlayerUser) {
        val currentRoom = gameRoomDataSource.getRoomByCode(playerUser.code)
        currentRoom?.players?.add(playerUser)
        if (currentRoom == null) {
            throw Exception("Game room not found")
        } else println("Player $playerUser")
    }

    suspend fun onEventPlayer(event: GameEventPlayer){
        when(event){
            is GameEventPlayer.OnSendAnswer -> {sendAnswer(event.answer)}
            is GameEventPlayer.OnShowListPlayers -> {showPlayers(event.playerUser.code)}
        }
    }

    private fun sendAnswer(answer: Answer?){
        answer?.let { answerDataSource.addAnswer(it) }
    }

    private suspend fun showPlayers(code: String) {
        try {
            val currentRoom = gameRoomDataSource.getRoomByCode(code)
            val players = currentRoom?.players
            //val playersDTO = players?.map { it.toDTO() }
            currentRoom?.host?.session?.sendSerialized(players.toBaseResult("USERS_SENT"))
            players?.forEach {
                it.session?.sendSerialized(players.toBaseResult("USERS_SENT"))
            }
        }catch (e: Exception){
            println(e)
        }

    }


}