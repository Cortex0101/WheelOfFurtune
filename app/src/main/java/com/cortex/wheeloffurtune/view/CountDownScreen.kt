package com.cortex.wheeloffurtune.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cortex.wheeloffurtune.utils.Utility
import com.cortex.wheeloffurtune.utils.Utility.formatTime
import com.cortex.wheeloffurtune.view.components.CountDownIndicator
import com.cortex.wheeloffurtune.viewmodel.CountDownViewModel

@Composable
fun CountDownView(viewModel: CountDownViewModel) {
    val time by viewModel.time.observeAsState(Utility.TIME_COUNTDOWN.formatTime())
    val progress by viewModel.progress.observeAsState(1.00F)
    val isPlaying by viewModel.isPlaying.observeAsState(false)
    val celebrate by viewModel.celebrate.observeAsState(false)

    CountDownView(time = time, progress = progress, isPlaying = isPlaying, celebrate = celebrate) {
        viewModel.handleCountDownTimer()
    }
}

@Composable
fun CountDownView(
    time: String,
    progress: Float,
    isPlaying: Boolean,
    celebrate: Boolean,
    optionSelected: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (celebrate){
            optionSelected()
        }

        CountDownIndicator(
            modifier = Modifier.padding(start = 200.dp, top = 5.dp),
            progress = progress,
            time = time,
            size = 50,
            stroke = 6
        )
    }

}