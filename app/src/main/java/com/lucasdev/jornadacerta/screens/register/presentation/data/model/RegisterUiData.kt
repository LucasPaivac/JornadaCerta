package com.lucasdev.jornadacerta.screens.register.presentation.data.model

data class RegisterUiData(
    val id: Int = 0,
    val date: String?,
    val startTime: String?,
    val lunchStartTime: String?,
    val lunchEndTime: String?,
    val endTime: String?,
    val workload: String?,
    val estimatedExitTime: String?,
){
    val isWorkInProgress: Boolean
        get() = startTime != null && endTime == null
}
