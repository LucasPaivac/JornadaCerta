package com.lucasdev.jornadacerta.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lucasdev.jornadacerta.common.data.local.room.TimeRegisterDao
import com.lucasdev.jornadacerta.common.data.local.room.TimeRegisterDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    fun provideDataBase(application: Application): TimeRegisterDataBase {
        return Room.databaseBuilder(
            application.applicationContext,
            TimeRegisterDataBase::class.java,"database-time-register"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    fun provideDao(roomDataBase: TimeRegisterDataBase): TimeRegisterDao{
        return roomDataBase.getTimeRegisterDao()
    }

    @Provides
    fun provideDispatcherIO(): CoroutineDispatcher{
        return Dispatchers.IO
    }
}