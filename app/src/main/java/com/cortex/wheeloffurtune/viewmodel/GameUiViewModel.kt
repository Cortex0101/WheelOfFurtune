package com.cortex.wheeloffurtune.viewmodel

import androidx.lifecycle.ViewModel
import com.cortex.wheeloffurtune.utils.getRandomWord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class GameUiViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState

    fun newWord(words: Array<String>) {
        val word = getRandomWord(words);
        _uiState.update { currentState ->
            currentState.copy(word = word.word, category = word.category)
        }
    }

    fun guessLetter(letter: Char, currentReward: Int) {
        if (_uiState.value.guessedLetters.contains(letter, true))
            return

        if (_uiState.value.word.contains(letter, true)) {
            _uiState.update { currentState ->
                currentState.copy(
                    money = currentState.money + (currentReward.toInt() * currentState.word.count { it.equals(letter, true) })
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(lives = currentState.lives - 1)
            }
        }

        _uiState.update { currentState ->
            currentState.copy(guessedLetters = currentState.guessedLetters + letter)
        }
    }

    fun resetState() {
        _uiState.value = GameUiState()
    }
}