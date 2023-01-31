package com.kk.models

data class Answer(
    val user: PlayerUser,
    val value: String,
    val isCorrect: Boolean = false
)