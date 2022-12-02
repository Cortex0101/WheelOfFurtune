package com.cortex.wheeloffurtune

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cortex.wheeloffurtune.view.*
import com.cortex.wheeloffurtune.viewmodel.CountDownViewModel
import com.cortex.wheeloffurtune.viewmodel.GameUiViewModel
import com.cortex.wheeloffurtune.viewmodel.KeyboardViewModel
import com.cortex.wheeloffurtune.viewmodel.WheelViewModel
import java.util.*

class MainActivity : ComponentActivity() {
    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val words = resources.getStringArray(R.array.words)
        val gameUiViewModel = GameUiViewModel()
        val keyboardViewModel = KeyboardViewModel()
        val wheelViewModel = WheelViewModel()
        val countDownViewModel = CountDownViewModel()
        gameUiViewModel.newWord(words)

        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "start"
            ) {
                composable("start") {
                    StartScreen(navController)
                }

                composable(route = "game") {
                    Game(navController = navController, gameUiViewModel = gameUiViewModel, keyboardViewModel = keyboardViewModel, wheelViewModel = wheelViewModel, countDownViewModel = countDownViewModel)
                }

                composable(route = "gameOver") {
                    val str = if (gameUiViewModel.uiState.value.lives == 0) {
                        stringResource(id = R.string.game_over_message,
                            gameUiViewModel.uiState.value.guessedLetters.lowercase().toList().intersect(
                                gameUiViewModel.uiState.value.word.lowercase().toList().toSet()
                            ).toSet().size,
                            gameUiViewModel.uiState.value.word.toSet().size,
                            gameUiViewModel.uiState.value.money,
                            gameUiViewModel.uiState.value.word)
                    } else {
                        stringResource(id = R.string.victory_message,
                            gameUiViewModel.uiState.value.lives,
                            gameUiViewModel.uiState.value.money)
                    }

                    GameOverScreen(navController = navController,
                        title = stringResource(id = if (gameUiViewModel.uiState.value.lives == 0) R.string.game_over else R.string.victory),
                        message = str,
                        onClick = {
                            gameUiViewModel.resetState()
                            keyboardViewModel.resetState()
                            wheelViewModel.resetState()
                            gameUiViewModel.newWord(words)
                        })
                }
            }
        }
    }
}