package com.cortex.wheeloffurtune.viewmodel

import androidx.lifecycle.ViewModel
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

    companion object{
        data class Word(val category: String, val word: String)

        fun getRandomWord(words: Array<String>): Word {
            val randomWord = words.random()
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