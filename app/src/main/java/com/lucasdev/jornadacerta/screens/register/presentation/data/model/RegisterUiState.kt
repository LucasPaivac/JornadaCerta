package com.lucasdev.jornadacerta.screens.register.presentation.data.model

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import com.lucasdev.jornadacerta.common.model.RegisterUiData

data class RegisterUiState(
    val register: RegisterUiData? = null,
    val isError: Boolean = false,
    val errorMessage: String? = "Something went wrong"
){
    val welcomeMessage: String = when {
        register == null -> "Vamos iniciar o dia?"
        register.isWorkInProgress -> "Entrada às ${register.startTime}"
        else -> "Até amanhã!"
    }

    val outMessage: String = when {
        register == null -> "Sair às --:--"
        register.isWorkInProgress -> "Sair às ${register.estimatedExitTime}"
        else -> ""
    }

    val labelButton: String = when {
        register == null -> "Registrar entrada"
        register.isWorkInProgress -> "Registrar saída"
        else -> "Jornada finalizada"
    }
    val isButtonEnabled: Boolean = register == null || register.isWorkInProgress

    @Composable
    @ReadOnlyComposable
    fun getButtonColor(): Pair<Color, Color> {
         when {
            register == null -> return MaterialTheme.colorScheme.primary to MaterialTheme.colorScheme.onPrimary
            register.isWorkInProgress -> return MaterialTheme.colorScheme.secondary to MaterialTheme.colorScheme.onSecondary
            else -> return MaterialTheme.colorScheme.primary to MaterialTheme.colorScheme.onPrimary
        }
    }
}

