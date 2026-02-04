package com.lucasdev.jornadacerta.common.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database([TimeRegisterEntity::class], version = 1)
abstract class TimeRegisterDataBase: RoomDatabase() {

    abstract fun getTimeRegisterDao(): TimeRegisterDao
}