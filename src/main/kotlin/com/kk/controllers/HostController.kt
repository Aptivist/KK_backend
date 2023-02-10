package com.kk.controllers

import com.google.gson.Gson
import com.kk.controllers.events.GameEventHost
import com.kk.data.AnswerDataSource
import com.kk.data.GameRoomDataSource
import com.kk.data.models.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.delay
import java.util.*
import kotlin.random.Random

class HostController(
    private val gameRoomDataSource: GameRoomDataSource,
    private val answerDataSource: AnswerDataSource
) {

    companion object {
        const val TIME_MILLIS = 1000L
    }

    suspend fun handleGameRoom(hostUser: HostUser, rules: Rules) {
        val code = "1234"//generateRandomCode()
        val newRoom = GameRoom(code = code, host = hostUser, rules = rules)
        hostUser.code = code
        gameRoomDataSource.addRoom(newRoom)
        hostUser.session?.sendSerialized(code.toBaseResult("GAME_ROOM_CREATED"))
    }


    suspend fun onEventHost(event: GameEventHost) {
        when (event) {
            is GameEventHost.OnStartRound -> {
                startRound(event.hostUser.code ?: "")
            }

            is GameEventHost.AddPoint -> {
                addPoint(event.playerIdPoint, event.host)
            }

            is GameEventHost.NoPoints -> {
                noPoints(event.hostUser.code ?: "")
            }
        }
    }

    private fun generateRandomCode(): String {
        val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..6)
            .map { _ -> Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
            .uppercase(Locale.getDefault())
    }

    private suspend fun startRound(code: String) {
        val currentRoom = gameRoomDataSource.getRoomByCode(code) ?: return
        val timerSeconds = currentRoom.rules.timerSeconds
        val timer = KKTimer(timerSeconds)
        val players = currentRoom.players

        currentRoom.isInitialized = true
        broadcastTimer(currentRoom.host, players, timer)
        waitForTimerEnd(timer)
        showAnswers(currentRoom, code)
    }

    private suspend fun broadcastTimer(host: HostUser?, players: List<PlayerUser >?, timer: KKTimer) {
        while (timer.time != -1) {
            host?.session?.sendSerialized(timer.toBaseResult("OK"))
            players?.forEach { player -> player.session?.sendSerialized(timer.toBaseResult("OK")) }
            timer.time--
            delay(TIME_MILLIS)
        }
    }

    private suspend fun waitForTimerEnd(timer: KKTimer) {
        delay((timer.time + 1) * TIME_MILLIS)
    }

    private suspend fun showAnswers(currentRoom: GameRoom, code: String) {
        val currentAnswers = answerDataSource.getAnswersByCode(code)
        val statusType = if (currentAnswers.isEmpty()) "NO_ANSWERS" else "OK"
        currentRoom.host.session?.sendSerialized(currentAnswers.sortedByDescending { it.timeStamp }.reversed().toBaseResult(statusType))
        answerDataSource.removeAll(currentAnswers)
    }


    private suspend fun addPoint(playerId: String, hostUser: HostUser) {
        val currentRoom = gameRoomDataSource.getRoomByCode(hostUser.code ?: "") ?: return
        val roundPlayerWon = currentRoom.players.find { it.id == playerId } ?: return
        roundPlayerWon.points?.plus(1)

        val gameIsFinished = roundPlayerWon.points == currentRoom.rules.points
        notifyWinner(currentRoom, roundPlayerWon, gameIsFinished)

        if (gameIsFinished) {
            gameRoomDataSource.removeRoom(currentRoom)
            currentRoom.host.session?.close(CloseReason(CloseReason.Codes.NORMAL, "NORMAL_CLOSURE"))
        }
    }

    private suspend fun notifyWinner(currentRoom: GameRoom, roundPlayerWon: PlayerUser, gameIsFinished: Boolean) {
        val result = GameResult(
            listPlayers = if (gameIsFinished) currentRoom.players else emptyList(),
            roundPlayerWon = roundPlayerWon
        )
        val statusType = if (gameIsFinished) "GAME_FINISHED" else "WINNER"
        currentRoom.players.forEach { player ->
            player.session?.sendSerialized(result.toBaseResult(statusType))
        }
    }


    private suspend fun noPoints(code: String) {
        val currentRoom = gameRoomDataSource.getRoomByCode(code) ?: return
        currentRoom.players.forEach { player ->
            player.session?.sendSerialized(
                GameResult().toBaseResult("NO_WINNER")
            )
        }
    }


}