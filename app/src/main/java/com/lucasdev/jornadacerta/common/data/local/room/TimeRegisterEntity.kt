package com.lucasdev.jornadacerta.common.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "time_register")
data class TimeRegisterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String,
    val startTime: String,
    val lunchStartTime: String?,
    val lunchEndTime: String?,
    val endTime: String?,
    val workload: String
)
