package com.cortex.wheeloffurtune.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class GameUiViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState

    fun newWord(words: Array<String>) {
        val word = getRandomWord(words)
        _uiState.update { currentState ->
            currentState.copy(word = word.word, category = word.category)
        }
        waitForSpin()
    }

    fun guessLetter(letter: Char, currentReward: Int) {
        if (_uiState.value.guessedLetters.contains(letter, true))
            return

        if (_uiState.value.word.contains(letter, true)) {
            _uiState.update { currentState ->
                currentState.copy(
                    money = currentState.money + (currentReward * currentState.word.count { it.equals(letter, true) })
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

        if (_uiState.value.lives == 0 ||
            _uiState.value.guessedLetters.lowercase().toList().containsAll(
                _uiState.value.word.lowercase().toList())) {
            gameOver()
        } else {
            waitForSpin()
        }
    }

    private fun waitForSpin() {
        _uiState.update { currentState ->
            currentState.copy(gameState = GameState.WAITING_FOR_SPIN)
        }
    }

    private fun gameOver() {
        _uiState.update { currentState ->
            currentState.copy(gameState = GameState.GAME_OVER)
        }
    }

    fun waitForGuess() {
        _uiState.update { currentState ->
            currentState.copy(gameState = GameState.GUESSING)
        }
    }

    fun resetState() {
        _uiState.value = GameUiState()
    }

    fun checkGameStatus(navController: NavController) {
        if (_uiState.value.gameState == GameState.GAME_OVER) {
            navController.navigate("gameOver")
        }
    }

    companion object{
        private val rand = Random(System.currentTimeMillis())
        data class Word(val category: String, val word: String)

        fun getRandomWord(words: Array<String>): Word {
            val randomWord = words.random(rand)
            val wordAndCategory = randomWord.split("\\s".toRegex()).toTypedArray()
            return Word(wordAndCategory[0], wordAndCategory[1])
        }

        fun extendWordToSize(word: String, size: Int): String {
            val builder = StringBuilder()
            builder.append("||")
            builder.append(word)
            builder.append("||")
            val remaining = size - builder.length

            if (remaining < 0)
                throw Exception("Cant fit the word")

            for (i in 1..remaining)
                builder.append('|')
            return builder.toString()
        }

        fun replaceUnderscoresWithSpace(word: String): String {
            return word.replace('_', ' ')
        }
    }
}