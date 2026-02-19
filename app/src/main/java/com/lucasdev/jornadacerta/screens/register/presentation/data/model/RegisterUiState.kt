package com.lucasdev.jornadacerta.screens.register.presentation.data.model

data class RegisterUiState(
    val welcomeMessage: String = "Registro de entrada pendente",
    val register: RegisterUiData? = null,
    val statusBar: Float = 0f,
    val isError: Boolean = false,
    val errorMessage: String? = "Something went wrong"
)
