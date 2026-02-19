package com.lucasdev.jornadacerta.common.utils

import com.lucasdev.jornadacerta.common.data.local.room.model.RegisterEntity
import com.lucasdev.jornadacerta.common.model.Register
import com.lucasdev.jornadacerta.screens.register.presentation.data.model.RegisterUiData
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun RegisterEntity.toDomain() = Register(
    id = id,
    date = LocalDate.parse(date),
    startTime = LocalTime.parse(startTime),
    lunchStartTime = LocalTime.parse(lunchStartTime),
    lunchEndTime = LocalTime.parse(lunchEndTime),
    endTime = LocalTime.parse(lunchEndTime),
    workload = LocalTime.parse(workload)
)

fun Register.toEntity() = RegisterEntity(
    id = id,
    date = date.toString(),
    startTime = startTime.toString(),
    lunchStartTime = lunchStartTime.toString(),
    lunchEndTime = lunchEndTime.toString(),
    endTime = endTime.toString(),
    workload = workload.toString()
)

fun Register.toUiData(): RegisterUiData{
    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    return RegisterUiData(
        id = id,
        date = date.toString(),
        startTime = startTime.format(formatter),
        lunchStartTime = lunchStartTime?.format(formatter),
        lunchEndTime = lunchEndTime?.format(formatter),
        endTime = endTime?.format(formatter),
        workload = workload.toString(),
    )
}