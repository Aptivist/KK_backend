package com.kk

import io.ktor.server.application.*
import com.kk.plugins.*
import org.koin.core.Koin

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureKoin()
    configureSockets()
    configureSerialization()
    configureRouting()
}
