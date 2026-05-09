package com.example.focusmate.di

import com.example.focusmate.domain.repository.TaskRepository
import com.example.focusmate.domain.usecase.task.AddTaskUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /*
    ========================================
    ADD TASK USE CASE
    ========================================
    */

    @Provides
    @Singleton
    fun provideAddTaskUseCase(

        repository: TaskRepository

    ): AddTaskUseCase {

        return AddTaskUseCase(
            repository
        )
    }
}