package com.kk.models

import kotlinx.serialization.Serializable

@Serializable
data class BaseResult <T>(
    val status: String,
    val data: T
)

fun <T> T.toBaseResult(status: String){
    BaseResult(status, this)
}