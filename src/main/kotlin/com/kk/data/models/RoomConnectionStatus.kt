package com.kk.data.models

sealed class RoomConnectionStatus {
    data class Success(val message: String = "CONNECTED"): RoomConnectionStatus()
    data class Error(val message:String): RoomConnectionStatus()
}
