package com.lucasdev.jornadacerta

import android.app.Application
import androidx.room.Room
import com.lucasdev.jornadacerta.common.data.local.room.TimeRegisterDataBase

class TimerRegisterApplication: Application() {

    val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            TimeRegisterDataBase::class.java,"database-time-register"
        ).build()
    }


}