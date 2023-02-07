package com.kk.data.models

import kotlinx.serialization.Serializable

@Serializable
data class BaseResult <T>(
    val status: String,
    val data: T
)

fun <T> T.toBaseResult(status: String): BaseResult<T> {
    return BaseResult(status, this)
}