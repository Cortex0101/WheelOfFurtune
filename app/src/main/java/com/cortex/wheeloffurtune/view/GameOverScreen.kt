package com.cortex.wheeloffurtune.view

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cortex.wheeloffurtune.viewmodel.GameUiViewModel

@Composable
fun GameOverScreen(
    navController: NavController,
    title: String,
    message: String,
    onClick: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp))
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Text(text = message,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally))

            ElevatedButton(
                onClick = {
                    navController.navigate("game")
                    onClick()
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(vertical = 4.dp).align(Alignment.CenterHorizontally),
            ) {
                Text(
                    text = "Play again",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}