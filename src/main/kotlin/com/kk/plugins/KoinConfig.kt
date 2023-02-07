package com.kk.plugins

import com.kk.di.controllerModule
import com.kk.di.dataSourceModule
import io.ktor.server.application.*
import org.koin.core.context.startKoin
import org.koin.ktor.plugin.Koin


fun Application.configureKoin(){
    install(Koin){
        modules(controllerModule, dataSourceModule)
    }
}