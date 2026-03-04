package com.lucasdev.jornadacerta.screens.history.data.model

import com.lucasdev.jornadacerta.common.model.RegisterUiData

data class HistoryUiState(
    val registers: List<RegisterUiData> = emptyList(),
    val emptyMessage: String = "Sem registros",
    val isError: Boolean = false,
    val errorMessage: String? = "Something went wrong"
)
