package com.example.focusmate.di

import com.example.focusmate.data.repository.FocusRepositoryImpl
import com.example.focusmate.data.repository.MotivationRepositoryImpl
import com.example.focusmate.data.repository.SettingsRepositoryImpl
import com.example.focusmate.data.repository.TaskRepositoryImpl
import com.example.focusmate.domain.repository.FocusRepository
import com.example.focusmate.domain.repository.MotivationRepository
import com.example.focusmate.domain.repository.SettingsRepository
import com.example.focusmate.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /*
    ========================================
    TASK REPOSITORY
    ========================================
    */

    @Binds
    @Singleton
    abstract fun bindTaskRepository(

        repositoryImpl: TaskRepositoryImpl

    ): TaskRepository

    /*
    ========================================
    MOTIVATION REPOSITORY
    ========================================
    */

    @Binds
    @Singleton
    abstract fun bindMotivationRepository(

        repositoryImpl: MotivationRepositoryImpl

    ): MotivationRepository

    /*
    ========================================
    FOCUS REPOSITORY
    ========================================
    */

    @Binds
    @Singleton
    abstract fun bindFocusRepository(

        repositoryImpl: FocusRepositoryImpl

    ): FocusRepository

    /*
    ========================================
    SETTINGS REPOSITORY
    ========================================
    */

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(

        repositoryImpl: SettingsRepositoryImpl

    ): SettingsRepository
}
