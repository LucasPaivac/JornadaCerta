package com.lucasdev.jornadacerta.screens.register.presentation

import android.window.OnBackInvokedDispatcher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucasdev.jornadacerta.common.data.repository.RegisterRepository
import com.lucasdev.jornadacerta.common.utils.toUiData
import com.lucasdev.jornadacerta.screens.register.presentation.data.model.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: RegisterRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiRegister = MutableStateFlow(RegisterUiState())
    val uiRegister: StateFlow<RegisterUiState> = _uiRegister

    val currentTime = flow {
        while (true) {
            emit(LocalTime.now())
            delay(1000)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LocalTime.now()
    )

    init {
        loadCurrentRegister()
    }

    private fun loadCurrentRegister(date: LocalDate = LocalDate.now()) {
        val dateString: String = date.toString()

        viewModelScope.launch(dispatcher) {
            val result = repository.getRegisterByDate(dateString)
            if (result.isSuccess) {
                val register = result.getOrNull()
                if (register != null) {
                    val registerUiData = register.toUiData()

                    _uiRegister.update {
                        it.copy(
                            welcomeMessage =
                                if (registerUiData.isWorkInProgress) {
                                    "Você entrou às ${registerUiData.startTime}"
                                } else {
                                    "Jornada finalizada"
                                       },
                            register = registerUiData,
                        )
                    }

                } else {
                    _uiRegister.value = RegisterUiState()
                }
            } else {
                _uiRegister.value = RegisterUiState(isError = true)
                val ex = result.exceptionOrNull()
                ex?.printStackTrace()
            }
        }

    }

    private fun percentCalculate(
        startTime: LocalTime,
        workload: LocalTime
    ): Float {
        val progressTimeMinutes = Duration.between(startTime, LocalTime.now()).toMinutes() % 60
        val workLoadMinutes = workload.hour * 60 + workload.minute
        val progress: Float = (progressTimeMinutes.toFloat() / workLoadMinutes.toFloat()) * 100

        return progress
    }

}
