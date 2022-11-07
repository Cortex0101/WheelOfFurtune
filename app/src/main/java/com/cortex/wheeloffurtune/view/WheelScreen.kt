package com.cortex.wheeloffurtune.view

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cortex.wheeloffurtune.viewmodel.KeyboardViewModel
import com.cortex.wheeloffurtune.viewmodel.WheelViewModel

@Composable
fun Wheel(
    @DrawableRes id: Int,
    rotationDegrees: Float = 0f,
    clickTogglePlayPause: (() -> Unit)
) {
    Image(
        modifier = Modifier
            .fillMaxSize(1f)
            .rotate(rotationDegrees)
            .aspectRatio(1f)
            .clickable(onClick = clickTogglePlayPause),
        painter = painterResource(id),
        contentDescription = "",
    )
}

@Composable
fun WheelAnimation(
    @DrawableRes id: Int,
    target: Float,
    isPlaying: Boolean = false,
    clickTogglePlayPause: (() -> Unit)
) {
    // Allow resume on rotation
    var currentRotation by remember { mutableStateOf(0f) }
    var spinCount by remember { mutableStateOf(0) }

    val rotation = remember { Animatable(currentRotation) }

    var done = false

    LaunchedEffect(isPlaying) {
        rotation.animateTo(
            targetValue = target,
            animationSpec = tween(
                durationMillis = 2000,
                easing = LinearOutSlowInEasing
            )
        ) {
            currentRotation = value
            if (currentRotation == target && !done) {
                clickTogglePlayPause()
                done = true
            }
        }
    }

    Wheel(rotationDegrees = rotation.value, id = id) {
        clickTogglePlayPause()
    }
}


@Composable
fun SpinningWheel(modifier: Modifier = Modifier,
                  wheelViewModel: WheelViewModel = viewModel()
) {
    val wheelUiState by wheelViewModel.uiState.collectAsState()

    var playingState by remember { mutableStateOf(false) }

    WheelAnimation(
        isPlaying = playingState,
        id = wheelUiState.wheelId,
        target = wheelUiState.wheelRotationDegree
    ) {
        if (!playingState) {
            wheelViewModel.spin()
            println(wheelUiState.wheelRotationDegree)
        }

        playingState = !playingState
    }
}