package com.lucasdev.jornadacerta

import android.app.Application
import androidx.room.Room
import com.lucasdev.jornadacerta.common.data.local.room.TimeRegisterDataBase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TimerRegisterApplication: Application()