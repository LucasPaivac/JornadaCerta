package com.lucasdev.jornadacerta.screens.register.presentation.data.model

data class RegisterUiState(
    val welcomeMessage: String = "Aguardando início da jornada...",
    val register: RegisterUiData? = null,
    val isError: Boolean = false,
    val errorMessage: String? = "Something went wrong"
)
