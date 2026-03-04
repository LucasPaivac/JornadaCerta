package com.lucasdev.jornadacerta.common.model

import java.time.LocalDate
import java.time.LocalTime

data class Register(
    val id: Int = 0,
    val date: LocalDate,
    val startTime: LocalTime,
    val lunchStartTime: LocalTime?,
    val lunchEndTime: LocalTime?,
    val endTime: LocalTime?,
    val workload: LocalTime,
    val balance: Long?
)
