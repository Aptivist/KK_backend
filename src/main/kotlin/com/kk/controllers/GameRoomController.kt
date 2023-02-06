package com.kk.controllers

import com.kk.events.GameEventPlayer
import com.kk.events.GameEventHost
import com.kk.models.GameRoom
import com.kk.models.HostUser
import com.kk.models.PlayerUser
import com.kk.models.Rules
import com.kk.models.events.Answer
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.delay
import java.util.*

class GameRoomController() {
    private val gameRooms = Collections.synchronizedSet<GameRoom>(LinkedHashSet())
    private val answers = Collections.synchronizedSet<Answer>(LinkedHashSet())

    private var currentRoom: GameRoom? = null

    fun handleGameRoom(hostUser: HostUser, rules: Rules) {
        gameRooms.add(GameRoom(code = hostUser.code, host = hostUser, rules =  rules))
        println("Host $hostUser")
    }

    fun handlePlayerConnection(playerUser: PlayerUser) {
        currentRoom = gameRooms.find { room -> room.code == playerUser.code }
        currentRoom?.players?.add(playerUser)
        if (currentRoom == null) {
            throw Exception("Game room not found")

        } else println("Player $playerUser")
    }


    suspend fun onEventHost(event: GameEventHost) {
        when(event){
            is GameEventHost.OnStartGame -> {startGame(event.hostUser.code)}
            is GameEventHost.OnStartRound -> {startRound(event.hostUser.code)}
            is GameEventHost.AddPoint -> {addPoint(event.playerIdPoint, event.code)}
            is GameEventHost.NoPoints -> {}
        }
    }

    private suspend fun addPoint(playerId: String, code: String){
        val currentRoom  = gameRooms.find { it.code == code }
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

    private suspend fun startGame(code: String){
        currentRoom = gameRooms.find { room -> room.code == code }

        val players = currentRoom?.players
        currentRoom?.isInitialized = true
        currentRoom?.host?.session?.send(" {\"result\":\"Ok\"}")
        players?.forEach {
            it.session?.send(" {\"result\":\"Ok\"}")
        }
        print("Game was initialized")
    }

    private suspend fun startRound(code: String){
        currentRoom = gameRooms.find { room -> room.code == code }
        currentRoom?.isInitialized = true
        var limitTimer = currentRoom?.rules?.timerSeconds ?: 10
        val players = currentRoom?.players


        while (limitTimer != -1){
            currentRoom?.host?.session?.send("{\"timer\":\"$limitTimer\"}")
            players?.forEach { player -> player.session?.send(" {\"timer\":\"$limitTimer\"}") }
            limitTimer--
            delay(1000)
        }
        val currentAnswers = answers.filter { it.gameCode == currentRoom?.code }
        if (currentAnswers.isEmpty()){
            currentRoom?.host?.session?.send("No hay respuestas")
        }else{
            currentRoom?.host?.session?.sendSerialized(currentAnswers.sortedByDescending { it.timeStamp }.reversed())
            answers.removeAll(currentAnswers.toSet())
        }


    }

    fun MutableList<GameRoom>.getCurrentRoomByCode(code: String): GameRoom?{
        return this.find { room -> room.code == code }
    }

    suspend fun onEventPlayer(event: GameEventPlayer){
        when(event){
            is GameEventPlayer.OnSendAnswer -> {sendAnswer(event.answer)}
            is GameEventPlayer.OnShowListPlayers -> {showPlayers(event.playerUser.code)}
        }
    }

    private fun sendAnswer(answer: Answer?){
        answers.add(answer)
    }

    private suspend fun showPlayers(code: String) {
        try {
            currentRoom = gameRooms.find { room -> room.code == code }
            val players = currentRoom?.players
            currentRoom?.host?.session?.send("[Players]: $players")
            players?.forEach {
                it.session?.send("[Players]: $players")
            }
            print("Player list was sent")
        }catch (e: Exception){
            println(e)
        }

    }

    private fun removePlayer(playerUser: PlayerUser) {
        println("Removing ${playerUser.name}!")
        currentRoom?.players?.removeIf { it.session == playerUser.session }
    }

    private fun removeGameSession(code: String?) {
        currentRoom?.players?.removeIf { it.code == code }
        gameRooms.remove(currentRoom)
    }

}






