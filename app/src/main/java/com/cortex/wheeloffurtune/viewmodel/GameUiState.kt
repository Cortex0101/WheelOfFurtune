package com.cortex.wheeloffurtune.viewmodel

data class GameUiState (
    val lives: Int = 5,
    val money: Int = 0,
    val word: String = "",
    val category: String = "",
    val guessedLetters: String = ""
        )