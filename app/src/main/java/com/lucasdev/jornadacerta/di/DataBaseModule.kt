package com.lucasdev.jornadacerta.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lucasdev.jornadacerta.common.data.local.room.TimeRegisterDao
import com.lucasdev.jornadacerta.common.data.local.room.TimeRegisterDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    private val Context.dataStore by preferencesDataStore(name = "user_settings")

    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences>{
        return context.dataStore
    }
}