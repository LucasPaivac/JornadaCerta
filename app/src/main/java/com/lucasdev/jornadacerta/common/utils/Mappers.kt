package com.lucasdev.jornadacerta.common.utils

import com.lucasdev.jornadacerta.common.data.local.room.model.RegisterEntity
import com.lucasdev.jornadacerta.common.model.Register
import com.lucasdev.jornadacerta.common.model.RegisterUiData
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale.*
import kotlin.math.abs

fun RegisterEntity.toDomain() = Register(
    id = id,
    date = LocalDate.parse(date),
    startTime = LocalTime.parse(startTime),
    lunchStartTime = lunchStartTime?.takeIf { it != "null" && it.isNotBlank() }
        ?.let { LocalTime.parse(lunchStartTime) },
    lunchEndTime = lunchEndTime?.takeIf { it != "null" && it.isNotBlank() }
        ?.let { LocalTime.parse(lunchEndTime) },
    endTime = endTime?.takeIf { it != "null" && it.isNotBlank() }?.let { LocalTime.parse(endTime) },
    workload = LocalTime.parse(workload),
    balance = balanceSeconds
)

fun Register.toEntity(): RegisterEntity {
    val workloadSeconds = workload.toSecondOfDay().toLong()
    var calculatedBalance = 0L
    if (endTime != null){
        val totalWorkedSeconds = Duration.between(startTime, endTime).seconds
        calculatedBalance = totalWorkedSeconds - workloadSeconds
    }

    return RegisterEntity(
        id = id,
        date = date.toString(),
        startTime = startTime.toString(),
        lunchStartTime = lunchStartTime?.let { lunchStartTime.toString() },
        lunchEndTime = lunchEndTime.let { lunchEndTime.toString() },
        endTime = endTime.let { endTime.toString() },
        workload = workload.toString(),
        balanceSeconds = calculatedBalance
    )
}

fun Register.toUiData(): RegisterUiData {
    val formatterTime = DateTimeFormatter.ofPattern("HH:mm")

    val isNegative = if (balance != null) balance < 0 else false
    val absSeconds = if (balance != null) abs(balance) else 0L

    val hours = absSeconds / 3600
    val minutes = (absSeconds % 3600) / 60


    val formattedBalance = String.format(
        getDefault(),
        "%02d:%02d",
        hours,
        minutes
    )

    return RegisterUiData(
        id = id,
        date = date.toString(),
        startTime = startTime.format(formatterTime),
        lunchStartTime = lunchStartTime?.let { lunchStartTime.format(formatterTime) },
        lunchEndTime = lunchEndTime?.let { lunchEndTime.format(formatterTime) },
        endTime = endTime?.let { endTime.format(formatterTime) },
        workload = workload.toString(),
        estimatedExitTime = startTime
            .plusHours(workload.hour.toLong())
            .plusMinutes(workload.minute.toLong())
            .format(formatterTime),
        balance = formattedBalance,
        isBalanceNegative = isNegative
    )
}

fun RegisterUiData.toDomain() = Register(
    id = id,
    date = LocalDate.parse(date),
    startTime = LocalTime.parse(startTime),
    lunchStartTime = lunchStartTime?.let { LocalTime.parse(lunchStartTime) },
    lunchEndTime = lunchEndTime?.let { LocalTime.parse(lunchEndTime) },
    endTime = endTime?.let { LocalTime.parse(endTime) },
    workload = LocalTime.parse(workload),
    balance = balance?.let { LocalTime.parse(balance).toSecondOfDay().toLong() }
)

