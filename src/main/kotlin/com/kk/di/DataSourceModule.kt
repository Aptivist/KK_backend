package com.kk.di

import com.kk.data.AnswerDataSource
import com.kk.data.AnswerDataSourceImp
import com.kk.data.GameRoomDataSource
import com.kk.data.GameRoomDataSourceImp
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val dataSourceModule = module {
    singleOf(::GameRoomDataSourceImp){
        bind<GameRoomDataSource>()
    }
    singleOf(::AnswerDataSourceImp){
        bind<AnswerDataSource>()
    }
}