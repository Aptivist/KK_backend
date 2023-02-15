package com.kk.data.models.events

data class Answer(
    val answer: String?,
    val timeStamp: Long,
    val gameCode: String,
    var playerId: String?
)
