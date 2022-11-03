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
import androidx.compose.material3.*
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cortex.wheeloffurtune.ui.theme.WheelOfFurtuneTheme
import com.cortex.wheeloffurtune.view.CountDownView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val word = getRandomWord(resources.getStringArray(R.array.words))
            WheelOfFurtuneTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        UserHead(firstName = "Lucas",
                                 lastName = "Eiruff",
                                 modifier = Modifier.size(80.dp))
                        MoneyBar("Player balance")

                        Column(modifier = Modifier
                            .background(Color(getColor(R.color.teal_200)))
                            .border(4.dp, Color(getColor(R.color.teal_700)))) {
                            Grid(word = extendWordToSize("", 17).toList())
                            Grid(word = extendWordToSize(word.word, 17).toList())
                            Grid(word = extendWordToSize("", 17).toList())
                        }

                        Spacer(Modifier.size(10.dp))

                        Box(modifier = Modifier.fillMaxWidth(0.8f),
                            contentAlignment = Alignment.TopCenter) {
                            CategoryDisplay(category = word.category,
                                            backgroundGradientSide = Color(getColor(R.color.teal_700)),
                                            backgroundGradientMiddle = Color(getColor(R.color.teal_200)),
                                            borderGradientSide = Color(getColor(R.color.purple_500)),
                                            borderGradientMiddle = Color(getColor(R.color.purple_200)))
                            CountDownView()
                        }

                        Keyboard()

                        SpinningWheel()
                    }
                }
            }
        }
    }
}

@Composable
fun Keyboard() {
    Column() {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            val firstRow = "QWERTYUIOP"
            for (letter in firstRow) {
                ButtonWithElevation(text = letter.toString())
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            val secondRow = "ASDFGHJKL"
            for (letter in secondRow) {
                ButtonWithElevation(text = letter.toString())
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            val thirdRow = "ZXCVBNM"
            Spacer(modifier = Modifier.size(30.dp))
            for (letter in thirdRow) {
                ButtonWithElevation(text = letter.toString())
            }
        }
    }
}

@Composable
fun ButtonWithElevation(text: String) {
    Button(modifier = Modifier.width(30.dp),
        contentPadding = PaddingValues(0.dp),
        onClick = {
        //your onclick code here
    }, elevation = ButtonDefaults.buttonElevation(
        defaultElevation = 10.dp,
        pressedElevation = 15.dp,
        disabledElevation = 0.dp
    )) {
        Text(text = text, fontSize = 20.sp)
    }
}


@Composable
fun UserHead(
    firstName: String,
    lastName: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = colorResource(id = R.color.teal_200),
    strokeColor: Color = colorResource(id = R.color.teal_700),
) {
    Box(modifier, contentAlignment = Alignment.Center) {
        val initials = (firstName.take(1) + lastName.take(1)).uppercase()

        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(SolidColor(backgroundColor))
            drawCircle(strokeColor, style = Stroke(8f))
        }

        Text(text = initials,
             style = MaterialTheme.typography.titleLarge,
             color = Color.White)
    }
}

@Composable
fun MoneyBar(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = colorResource(id = R.color.purple_200)
){
    Box(
        modifier = modifier
            .fillMaxWidth(0.4f)
            .height(40.dp)
            .background(
                color,
                shape = RoundedCornerShape(60.dp)
            ),
        contentAlignment = Alignment.Center
    ){
        Text(text = text)
    }
}

@Composable
fun Grid(word: List<Char>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(17),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 2.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(word) { char ->
            LetterDisplay(letter = char, shown = true)
        }
    }
}

@Composable
fun CategoryDisplay(category: String, backgroundGradientSide: Color, backgroundGradientMiddle: Color, borderGradientSide: Color, borderGradientMiddle: Color) {
    Box(modifier = Modifier
        .background(
            Brush.horizontalGradient(
                listOf(
                    backgroundGradientSide,
                    backgroundGradientMiddle,
                    backgroundGradientSide
                )
            )
        )
        .fillMaxWidth(0.6f)
        .drawWithContent {
            drawContent()
            clipRect { // Not needed if you do not care about painting half stroke outside
                val strokeWidth = 10f
                var y = size.height // - strokeWidth
                // if the whole line should be inside component
                drawLine(
                    brush = Brush.horizontalGradient(
                        listOf(
                            borderGradientSide,
                            borderGradientMiddle,
                            borderGradientSide
                        )
                    ),
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Square,
                    start = Offset.Zero.copy(y = y),
                    end = Offset(x = size.width, y = y)
                )
                y = 0f
                drawLine(
                    brush = Brush.horizontalGradient(
                        listOf(
                            borderGradientSide,
                            borderGradientMiddle,
                            borderGradientSide
                        )
                    ),
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Square,
                    start = Offset.Zero.copy(y = y),
                    end = Offset(x = size.width, y = y)
                )
            }
        })
    {
        Text(
            text = replaceUnderscoresWithSpace(category),
            modifier = Modifier
                .padding(6.dp)
                .align(Alignment.Center),
        )
    }
}

@Composable
fun LetterDisplay(letter: Char, shown: Boolean) {
    Box(
        modifier = Modifier
            .border(width = 2.dp, color = Gray, shape = RoundedCornerShape(4.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            if (shown && letter != '|') // '|' is used when the box cant contain a letter
                letter.toString()
            else
                " ",
            modifier = Modifier
                .size(28.dp, 34.dp)
                .background(
                    if (letter != '|')
                        Color(187, 134, 252)
                    else
                        Color(55, 0, 179)
                ),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun Wheel(
    rotationDegrees: Float = 0f,
    clickTogglePlayPause: (() -> Unit)
) {
    Image(
        modifier = Modifier
            .fillMaxSize(1f)
            .rotate(rotationDegrees)
            .aspectRatio(1f)
            .clickable(onClick = clickTogglePlayPause),
        painter = painterResource(R.drawable.wof),
        contentDescription = "",
    )
}

@Composable
fun WheelAnimation(
    isPlaying: Boolean = false,
    clickTogglePlayPause: (() -> Unit)
) {
    // Allow resume on rotation
    var currentRotation by remember { mutableStateOf(0f) }

    val rotation = remember { Animatable(currentRotation) }

    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            // Infinite repeatable rotation when is playing
            rotation.animateTo(
                targetValue = currentRotation + 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(3000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            ) {
                currentRotation = value
            }
        } else {
            if (currentRotation > 0f) {
                // Slow down rotation on pause
                rotation.animateTo(
                    targetValue = currentRotation + 50,
                    animationSpec = tween(
                        durationMillis = 1250,
                        easing = LinearOutSlowInEasing
                    )
                ) {
                    currentRotation = value
                }
            }
        }
    }

    Wheel(rotationDegrees = rotation.value) {
        clickTogglePlayPause()
    }
}


@Composable
fun SpinningWheel() {
    var playingState by remember { mutableStateOf(false) }

    WheelAnimation(
        isPlaying = playingState
    ) {
        playingState = !playingState
    }
}















@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    //val word = getRandomWord(resources.getStringArray(R.array.words))
    val word = Word("Amusing", "Happy")
    WheelOfFurtuneTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                UserHead(firstName = "Lucas",
                    lastName = "Eiruff",
                    modifier = Modifier.size(80.dp))
                MoneyBar("Player balance")

                Column(modifier = Modifier
                    .background(Color.Red)
                    .border(4.dp, Color.Green)) {
                    Grid(word = extendWordToSize("", 17).toList())
                    Grid(word = extendWordToSize(word.word, 17).toList())
                    Grid(word = extendWordToSize("", 17).toList())
                }

                Spacer(Modifier.size(10.dp))

                Box(modifier = Modifier.fillMaxWidth(0.8f),
                    contentAlignment = Alignment.TopCenter) {
                    CategoryDisplay(category = word.category,
                        backgroundGradientSide = Color.Yellow,
                        backgroundGradientMiddle = Color.Blue,
                        borderGradientSide = Color.Yellow,
                        borderGradientMiddle = Color.Blue)
                    CountDownView()
                }

                ButtonWithElevation("A")
                SpinningWheel()
            }
        }
    }
}