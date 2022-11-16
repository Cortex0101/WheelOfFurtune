package com.cortex.wheeloffurtune.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cortex.wheeloffurtune.viewmodel.GameState
import com.cortex.wheeloffurtune.viewmodel.GameUiViewModel
import com.cortex.wheeloffurtune.viewmodel.KeyboardViewModel

@Composable
fun KeyboardScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    currentReward: Int,
    gameUiViewModel: GameUiViewModel,
    keyboardViewModel: KeyboardViewModel = viewModel()
) {
    val keyboardUiState by keyboardViewModel.uiState.collectAsState()
    val gameUiState by gameUiViewModel.uiState.collectAsState()

    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            for (letter in keyboardUiState.firstRowLetters) {
                KeyboardButton(letter = letter,
                               enabled = !keyboardUiState.inactiveLetters.contains(letter),
                                onClick = {
                        if (gameUiState.gameState == GameState.GUESSING) {
                            keyboardViewModel.pressLetter(letter)
                            gameUiViewModel.guessLetter(letter, currentReward)
                            gameUiViewModel.checkGameStatus(navController)
                        }})
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            for (letter in keyboardUiState.secondRowLetters) {
                KeyboardButton(letter = letter,
                               enabled = !keyboardUiState.inactiveLetters.contains(letter),
                                onClick = {
                        if (gameUiState.gameState == GameState.GUESSING) {
                            keyboardViewModel.pressLetter(letter)
                            gameUiViewModel.guessLetter(letter, currentReward)
                            gameUiViewModel.checkGameStatus(navController)
                        }})
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Spacer(modifier = Modifier.size(30.dp)) // Because there is one less letter here...
            for (letter in keyboardUiState.thirdRowLetters) {
                KeyboardButton(letter = letter,
                               enabled = !keyboardUiState.inactiveLetters.contains(letter),
                               onClick = {
                                   if (gameUiState.gameState == GameState.GUESSING) {
                                       keyboardViewModel.pressLetter(letter)
                                       gameUiViewModel.guessLetter(letter, currentReward)
                                       gameUiViewModel.checkGameStatus(navController)
                                   }})
            }
        }
    }
}


@Composable
fun KeyboardButton(letter: Char, enabled: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(modifier = modifier.width(30.dp),
        contentPadding = PaddingValues(0.dp),
        enabled = enabled,
        onClick = onClick,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 10.dp,
            pressedElevation = 15.dp,
            disabledElevation = 0.dp
        )) {
        Text(text = letter.toString(), fontSize = 20.sp)
    }
}
