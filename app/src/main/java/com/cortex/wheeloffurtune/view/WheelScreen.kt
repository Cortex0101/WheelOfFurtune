package com.cortex.wheeloffurtune.view

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.cortex.wheeloffurtune.viewmodel.WheelViewModel

@Composable
fun Wheel(
    @DrawableRes id: Int,
    rotationDegrees: Float = 0f,
) {
    Image(
        modifier = Modifier
            .fillMaxSize(1f)
            .rotate(rotationDegrees)
            .aspectRatio(1f),
        painter = painterResource(id),
        contentDescription = "",
    )
}

@Composable
fun WheelAnimation(
    @DrawableRes id: Int,
    target: Float
) {
    val rotation: Float by animateFloatAsState(
        targetValue = target,
        animationSpec = tween(
        durationMillis = 2000,
        easing = LinearOutSlowInEasing
        )
    )

    Wheel(rotationDegrees = rotation, id = id)
}

/*
    * Displays a small circular button with a play icon in the middle
 */
@Composable
fun CircularWheelButton(
    modifier: Modifier = Modifier,
    shown: Boolean,
    onClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Icon(
            modifier = modifier
                .fillMaxSize(0.3f)
                .aspectRatio(1f)
                .clickable {
                    onClick()
                },
            imageVector = Icons.Default.PlayArrow,
            tint = if (shown) Color.Yellow else Color.Transparent,
            contentDescription = "",
        )
    }
}


@Composable
fun SpinningWheel(modifier: Modifier = Modifier,
                  wheelViewModel: WheelViewModel
) {
    val wheelUiState by wheelViewModel.uiState.collectAsState()

    WheelAnimation(
        id = wheelUiState.wheelId,
        target = wheelUiState.wheelRotationDegree
    )
}