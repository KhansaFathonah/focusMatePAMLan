package com.example.focusmate.di

import com.example.focusmate.data.remote.api.ApiConfig
import com.example.focusmate.data.remote.api.MotivationApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMotivationApi(): MotivationApi {

        return ApiConfig.provideApi()
    }
}

