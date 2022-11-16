package com.cortex.wheeloffurtune

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cortex.wheeloffurtune.view.*
import com.cortex.wheeloffurtune.viewmodel.GameUiViewModel
import com.cortex.wheeloffurtune.viewmodel.KeyboardViewModel
import com.cortex.wheeloffurtune.viewmodel.WheelViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gameUiViewModel = GameUiViewModel()
        val keyboardViewModel = KeyboardViewModel()
        val wheelViewModel = WheelViewModel()
        gameUiViewModel.newWord(resources.getStringArray(R.array.words))
        gameUiViewModel.newWord(resources.getStringArray(R.array.words))

        setContent {
            Game(gameUiViewModel = gameUiViewModel, keyboardViewModel = keyboardViewModel, wheelViewModel = wheelViewModel)
        }
    }
}