package com.cortex.wheeloffurtune.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun StartScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp))) {
            Text(text = "Wheel of Fortune",
                 style = MaterialTheme.typography.headlineLarge,
                 modifier = Modifier.align(Alignment.CenterHorizontally))
            Text(text = "When the game starts, a word is randomly chosen from predefined categories " +
                    "and displayed along with the category. " + "The word is displayed with the " +
                    "letters hidden.")
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "The player can spin the wheel to determine the amount of money " +
                    "they will win if they guess a letter correctly. " +
                    "The player can then guess letters by clicking on the letters on the " +
            "keyboard. " + "If the letter is in the word, the letter is revealed, and " +
                    "the players balance is incremented by the amount the wheel landed on. " + "If the " +
            "letter is not in the word, the player loses a life.")
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "If the player loses all their lives, the game is over. If the player " +
                    "guesses the entire word, the game is won.")

            ElevatedButton(
                onClick = {
                          navController.navigate("game")
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(vertical = 4.dp).align(Alignment.CenterHorizontally),
            ) {
                Text(
                    text = "Play",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}