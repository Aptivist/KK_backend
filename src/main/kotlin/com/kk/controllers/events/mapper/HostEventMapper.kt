package com.kk.controllers.events.mapper

import com.kk.controllers.events.GameEventHost
import com.kk.data.models.HostUser
import com.kk.data.models.events.HostEvent

fun HostEvent.toEvent(host: HostUser): GameEventHost {
    return when(event){
        "START_GAME" -> GameEventHost.OnStartGame(host)
        "START_ROUND" -> GameEventHost.OnStartRound(host)
        "NEXT_ROUND" -> GameEventHost.NextRound(host)
        "ADD_POINT" -> GameEventHost.AddPoint(playerIdPoint?: "", host.code)
        "NO_POINTS" -> GameEventHost.NoPoints(host)
        "SHOW_ANSWERS" -> GameEventHost.ShowAnswers(host)
        //"NEXT_QUESTION" -> GameEventHost.
        else -> throw Exception("Error")
    }
}
