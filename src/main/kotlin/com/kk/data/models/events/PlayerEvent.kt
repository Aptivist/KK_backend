package com.kk.data.models.events


data class PlayerEvent(
    val event: String,
    val answer: Answer? = null,
    val code: String = ""
)
