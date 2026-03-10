package com.lucasdev.jornadacerta.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucasdev.jornadacerta.common.data.repository.RegisterRepository
import com.lucasdev.jornadacerta.common.utils.toUiData
import com.lucasdev.jornadacerta.screens.history.data.model.HistoryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: RegisterRepository,
    private val dispatcher: CoroutineDispatcher
): ViewModel() {

    private val _uiHistory = MutableStateFlow(HistoryUiState())
    val uiHistory: StateFlow<HistoryUiState> = _uiHistory

    fun loadAllRegisters(){

        viewModelScope.launch(dispatcher) {
            val result = repository.getAllRegisters()
            if (result.isSuccess){
                val registers = result.getOrNull() ?: emptyList()
                if (registers.isNotEmpty()){
                    val registersUiData = registers.map { it.toUiData() }

                    _uiHistory.value = HistoryUiState(
                        registers = registersUiData
                    )
                }else {
                    _uiHistory.value = HistoryUiState()
                }
            } else {
                _uiHistory.value = HistoryUiState(isError = true)
                val ex = result.exceptionOrNull()
                ex?.printStackTrace()
            }
        }
    }

}