package com.kk.controllers

import com.kk.controllers.events.GameEventHost
import com.kk.data.AnswerDataSource
import com.kk.data.GameRoomDataSource
import com.kk.data.models.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.delay

class HostController(
    private val gameRoomDataSource: GameRoomDataSource,
    private val answerDataSource: AnswerDataSource
) {

    fun handleGameRoom(hostUser: HostUser, rules: Rules) {
        val newRoom = GameRoom(code = hostUser.code, host = hostUser, rules = rules)
        gameRoomDataSource.addRoom(newRoom)
    }

    suspend fun onEventHost(event: GameEventHost) {
        when(event){
            is GameEventHost.OnStartRound -> {startRound(event.hostUser.code)}
            is GameEventHost.AddPoint -> {addPoint(event.playerIdPoint, event.code)}
            is GameEventHost.NoPoints -> {}
        }
    }

    private suspend fun startRound(code: String){
        val currentRoom = gameRoomDataSource.getRoomByCode(code)
        currentRoom?.isInitialized = true
        val timer = KKTimer(currentRoom?.rules?.timerSeconds ?: 10)
        val players = currentRoom?.players

        while (timer.time != -1){
            currentRoom?.host?.session?.sendSerialized(timer.toBaseResult("OK"))
            players?.forEach { player -> player.session?.sendSerialized(timer.toBaseResult("OK")) }
            timer.time--
            delay(1000)
        }

        val currentAnswers = answerDataSource.getAnswersByCode(code)
        if (currentAnswers.isEmpty()){
            currentRoom?.host?.session?.sendSerialized("No hay respuestas".toBaseResult("OK"))
        }else{
            currentRoom?.host?.session?.sendSerialized(currentAnswers.sortedByDescending { it.timeStamp }.reversed())
            answerDataSource.removeAll(currentAnswers)
        }
    }

    private suspend fun addPoint(playerId: String, code: String){
        val currentRoom = gameRoomDataSource.getRoomByCode(code)
        val playerWon = currentRoom?.players?.find { it.id == playerId }

        currentRoom?.players?.forEach { player ->
            if (player.id == playerId){
                player.points?.plus(1)
                player.session?.send("Ganaste")
            }else{
                player.session?.send("Punto para ${playerWon?.name}")
            }
        }
    }



}