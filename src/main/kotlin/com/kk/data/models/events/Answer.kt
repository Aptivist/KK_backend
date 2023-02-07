package com.kk.data.models.events

import kotlinx.serialization.Serializable

@Serializable
data class Answer(
    val answer: String?,
    val timeStamp: Long,
    val gameCode: String
)
