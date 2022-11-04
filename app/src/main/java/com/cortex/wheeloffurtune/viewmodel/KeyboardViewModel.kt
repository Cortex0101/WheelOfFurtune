package com.cortex.wheeloffurtune.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class KeyboardViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(KeyboardUiState())
    val uiState: StateFlow<KeyboardUiState> = _uiState.asStateFlow()

    init {
        resetState()
    }

    fun pressLetter(letter: Char) {
        if (_uiState.value.inactiveLetters.contains(letter))
            return

        _uiState.update { currentState ->
            currentState.copy(inactiveLetters = currentState.inactiveLetters + letter)
        }
    }

    fun resetState() {
        _uiState.value = KeyboardUiState()
    }
}