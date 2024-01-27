package com.example.bmicounter

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
class BmiViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BmiUiState())
    val uiState: StateFlow<BmiUiState> = _uiState.asStateFlow()

    fun addHeight() {
        _uiState.update { currentState ->
            currentState.copy(
                height = currentState.height + 1
            )
        }
    }

    fun deductHeight() {
        _uiState.update { currentState ->
            currentState.copy(
                height = currentState.height - 1
            )
        }
    }

    fun addAge() {
        _uiState.update { currentState ->
            currentState.copy(
                weight = currentState.weight + 1

            )
        }
    }

    fun reduceAge() {
        _uiState.update { currentState ->
            currentState.copy(
                weight = currentState.weight - 1
            )
        }
    }

    fun calculateBMI() {
        _uiState.update { currentState ->
            currentState.copy(
                bmi = currentState.weight /(currentState.height * currentState.height)
            )
        }
    }
}

data class BmiUiState(
    var height: Float = 0.0F,
    var weight: Float = 0.0F,
    var bmi:Float = 0.0F,
)
