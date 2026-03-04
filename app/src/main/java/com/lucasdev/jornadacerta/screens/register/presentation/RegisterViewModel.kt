package com.lucasdev.jornadacerta.screens.register.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lucasdev.jornadacerta.common.data.repository.RegisterRepository
import com.lucasdev.jornadacerta.common.data.repository.UserPreferencesRepository
import com.lucasdev.jornadacerta.common.utils.toDomain
import com.lucasdev.jornadacerta.common.utils.toUiData
import com.lucasdev.jornadacerta.common.model.RegisterUiData
import com.lucasdev.jornadacerta.screens.register.presentation.data.model.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: RegisterRepository,
    private val dispatcher: CoroutineDispatcher,
    private val userPrefRepo: UserPreferencesRepository
) : ViewModel() {

    private val _uiRegister = MutableStateFlow(RegisterUiState())
    val uiRegister: StateFlow<RegisterUiState> = _uiRegister

    private val _recentHistory = MutableStateFlow<List<RegisterUiData>>(emptyList())
    val recentHistory: StateFlow<List<RegisterUiData>> =    _recentHistory

    private val _uiSelectedWorkload = MutableStateFlow("08:48")
    val uiSelectedWorkload: StateFlow<String> = _uiSelectedWorkload

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
        viewModelScope.launch(dispatcher) {

            userPrefRepo.defaultWorkloadFlow.collect { favorito ->

                if (_uiRegister.value.register == null) {
                    _uiSelectedWorkload.value = favorito
                }
            }
        }
        loadCurrentRegister()
        loadRecentRegisters()
    }

    private fun loadCurrentRegister(date: LocalDate = LocalDate.now()) {
        val dateString: String = date.toString()

        viewModelScope.launch(dispatcher) {

            val result = repository.getRegisterByDate(dateString)
            if (result.isSuccess) {
                val register = result.getOrNull()
                if (register != null) {
                    val registerUiData = register.toUiData()

                    _uiRegister.value = RegisterUiState(
                        register = registerUiData,
                    )

                } else {
                    _uiRegister.value = RegisterUiState(
                        register = null
                    )
                }
            } else {
                _uiRegister.value = RegisterUiState(isError = true)
                val ex = result.exceptionOrNull()
                ex?.printStackTrace()
            }
        }

    }

    private fun loadRecentRegisters() {
        viewModelScope.launch(dispatcher) {
            val result = repository.getRecentRegisters()
            if (result.isSuccess){
                val registers = result.getOrNull() ?: emptyList()
                if (registers.isNotEmpty()){
                    val recentRegistersUiData = registers.map { it.toUiData() }

                    _recentHistory.value = recentRegistersUiData
                }else{
                    _recentHistory.value = emptyList()
                }
            }else{
                _recentHistory.value = emptyList()
                val ex = result.exceptionOrNull()
                ex?.printStackTrace()
            }
        }
    }

    fun updateWorkload(newWorkload: String) {
        viewModelScope.launch(dispatcher) {

            userPrefRepo.saveDefaultWorkload(newWorkload)

            _uiSelectedWorkload.value = newWorkload

            val currentState = _uiRegister.value

            if (currentState.register != null) {
                val registerData = currentState.register
                val updatedRegister = registerData.copy(workload = newWorkload)
                repository.insertOrUpdateRegister(updatedRegister.toDomain())
                loadCurrentRegister(LocalDate.parse(updatedRegister.date))
            }
        }
    }

    fun onRegisterEntryOut(customTime: String) {
        viewModelScope.launch(dispatcher) {
            val formatter = DateTimeFormatter.ofPattern("HH:mm")

            val currentState = _uiRegister.value

            if (currentState.register == null) {
                val newRegister = RegisterUiData(
                    date = LocalDate.now().toString(),
                    startTime = customTime,
                    workload = _uiSelectedWorkload.value
                )

                repository.insertOrUpdateRegister(newRegister.toDomain())
                loadRecentRegisters()
                loadCurrentRegister(LocalDate.parse(newRegister.date))

            } else if (currentState.register.isWorkInProgress) {

                val updatedRegister = currentState.register.copy(
                    endTime = customTime
                )

                repository.insertOrUpdateRegister(register = updatedRegister.toDomain())
                loadRecentRegisters()
                loadCurrentRegister(LocalDate.parse(updatedRegister.date))

            }

        }
    }

    fun updateEntry(newEntry: String){
        viewModelScope.launch(dispatcher) {
            val currentState = _uiRegister.value

            if (currentState.register != null) {
                val registerData = currentState.register
                val updatedRegister = registerData.copy(startTime = newEntry)
                repository.insertOrUpdateRegister(updatedRegister.toDomain())
                loadCurrentRegister(LocalDate.parse(updatedRegister.date))
            }
        }
    }



}
