package com.cortex.wheeloffurtune

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cortex.wheeloffurtune.view.*
import com.cortex.wheeloffurtune.viewmodel.GameUiViewModel
import com.cortex.wheeloffurtune.viewmodel.KeyboardViewModel
import com.cortex.wheeloffurtune.viewmodel.WheelViewModel
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val words = resources.getStringArray(R.array.words)
        val gameUiViewModel = GameUiViewModel()
        val keyboardViewModel = KeyboardViewModel()
        val wheelViewModel = WheelViewModel()
        gameUiViewModel.newWord(words)
        gameUiViewModel.newWord(words)

        setContent {
            Game(gameUiViewModel = gameUiViewModel, keyboardViewModel = keyboardViewModel, wheelViewModel = wheelViewModel)
        }
    }
}