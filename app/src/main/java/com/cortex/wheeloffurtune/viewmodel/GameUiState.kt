package com.cortex.wheeloffurtune.viewmodel

enum class GameState {
    GUESSING,
    GAME_OVER,
    WAITING_FOR_SPIN,
    NONE
}

data class GameUiState (
    val lives: Int = 5,
    val money: Int = 0,
    val word: String = "",
    val category: String = "",
    val guessedLetters: String = "",
    val gameState: GameState = GameState.NONE
        )