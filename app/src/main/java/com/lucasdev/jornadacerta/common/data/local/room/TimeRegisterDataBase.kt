package com.lucasdev.jornadacerta.common.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lucasdev.jornadacerta.common.data.local.room.model.RegisterEntity

@Database([RegisterEntity::class], version = 1)
abstract class TimeRegisterDataBase: RoomDatabase() {

    abstract fun getTimeRegisterDao(): TimeRegisterDao
}