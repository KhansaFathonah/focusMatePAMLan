package com.example.focusmate.di

import android.content.Context
import androidx.room.Room
import com.example.focusmate.data.local.dao.FocusSessionDao
import com.example.focusmate.data.local.dao.MotivationDao
import com.example.focusmate.data.local.dao.TaskDao
import com.example.focusmate.data.local.database.FocusMateDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /*
    ========================================
    DATABASE
    ========================================
    */

    @Provides
    @Singleton
    fun provideDatabase(

        @ApplicationContext
        context: Context

    ): FocusMateDatabase {

        return Room.databaseBuilder(

            context,

            FocusMateDatabase::class.java,

            "focusmate_db"

        )

            /*
            ====================================
            RESET OLD SCHEMA
            ====================================
            */

            .fallbackToDestructiveMigration()

            .build()
    }

    /*
    ========================================
    TASK DAO
    ========================================
    */

    @Provides
    @Singleton
    fun provideTaskDao(

        database: FocusMateDatabase

    ): TaskDao {

        return database.taskDao()
    }

    /*
    ========================================
    MOTIVATION DAO
    ========================================
    */

    @Provides
    @Singleton
    fun provideMotivationDao(

        database: FocusMateDatabase

    ): MotivationDao {

        return database.motivationDao()
    }

    /*
    ========================================
    FOCUS SESSION DAO
    ========================================
    */

    @Provides
    @Singleton
    fun provideFocusSessionDao(

        database: FocusMateDatabase

    ): FocusSessionDao {

        return database.focusSessionDao()
    }
}