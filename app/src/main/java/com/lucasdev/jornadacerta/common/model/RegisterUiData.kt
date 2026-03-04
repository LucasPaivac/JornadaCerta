package com.lucasdev.jornadacerta.common.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.text.format

data class RegisterUiData(
    val id: Int = 0,
    val date: String?,
    val startTime: String?,
    val lunchStartTime: String? = null,
    val lunchEndTime: String? = null,
    val endTime: String? = null,
    val workload: String?,
    val estimatedExitTime: String? = null,
    val balance: String? = null,
    val isBalanceNegative: Boolean = false
) {
    private val formaterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    val dateFormated: String? = date.let { LocalDate.parse(date) }.format(formaterDate)
    val isWorkInProgress: Boolean
        get() = startTime != null && endTime == null

    val balanceFormatted: String = if (startTime == null || endTime == null)
    {
        "--:--"
    } else if (isBalanceNegative)
    {
        "-$balance"
    } else
    {
        "+$balance"
    }

    @Composable
    @ReadOnlyComposable
    fun getBalanceColor(): Color = if (startTime == null || endTime == null) {
        Color.Unspecified
    } else if (isBalanceNegative) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.tertiary
    }
}