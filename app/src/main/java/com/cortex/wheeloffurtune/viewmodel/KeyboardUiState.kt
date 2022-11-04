package com.cortex.wheeloffurtune.viewmodel

data class KeyboardUiState (
    val firstRowLetters: String = "QWERTYUIOP",
    val secondRowLetters: String = "ASDFGHJKL",
    val thirdRowLetters: String = "ZXCVBNM",
    val allLetters: String = firstRowLetters + secondRowLetters + thirdRowLetters,
    val inactiveLetters: String = ""
    )