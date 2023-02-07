package com.kk.di

import com.kk.controllers.HostController
import com.kk.controllers.PlayerController
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val controllerModule = module {
    singleOf(::HostController)
    singleOf(::PlayerController)
}