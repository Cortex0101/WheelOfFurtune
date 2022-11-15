package com.cortex.wheeloffurtune

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cortex.wheeloffurtune.ui.theme.WheelOfFurtuneTheme
import com.cortex.wheeloffurtune.utils.Word
import com.cortex.wheeloffurtune.utils.extendWordToSize
import com.cortex.wheeloffurtune.utils.getRandomWord
import com.cortex.wheeloffurtune.utils.replaceUnderscoresWithSpace
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