package com.lucasdev.jornadacerta.common.data.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "time_register")
data class RegisterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String,
    val startTime: String,
    val lunchStartTime: String?,
    val lunchEndTime: String?,
    val endTime: String?,
    val workload: String
)