package com.cortex.wheeloffurtune

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.ColorInt
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import com.cortex.wheeloffurtune.Utility.formatTime
import com.cortex.wheeloffurtune.ui.theme.WheelOfFurtuneTheme
import kotlin.math.absoluteValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val word = getRandomWord(resources.getStringArray(R.array.words))
            //val word = Word("the category", "concentration")
            WheelOfFurtuneTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        UserHead(id = "test", firstName = "Lucas", lastName = "Eiruff")
                        MoneyBar(color = Color(getColor(R.color.purple_500)))
                        Column(modifier = Modifier
                            .background(Color(getColor(R.color.teal_200)))
                            .border(4.dp, Color(getColor(R.color.teal_700)))) {
                            Grid(word = extendWordToSize("", 17).toList())
                            Grid(word = extendWordToSize(word.word, 17).toList())
                            Grid(word = extendWordToSize("", 17).toList())
                        }
                        CategoryDisplay(category = word.category, backgroundGradientSide = Color(getColor(R.color.teal_700)), backgroundGradientMiddle = Color(getColor(R.color.teal_200)), borderGradientSide = Color(getColor(R.color.purple_500)), borderGradientMiddle = Color(getColor(R.color.purple_200)))
                        CountDownView()
                    }
                }
            }
        }
    }
}

@Composable
fun CountDownIndicator(
    modifier: Modifier = Modifier,
    progress: Float,
    time: String,
    size: Int,
    stroke: Int
){

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
    )

    Column(modifier = modifier) {
        Box {

            CircularProgressIndicatorBackGround(
                modifier = Modifier
                    .height(size.dp)
                    .width(size.dp),
                color = Color.Red,
                stroke
            )

            CircularProgressIndicator(
                progress = animatedProgress,
                modifier = Modifier
                    .height(size.dp)
                    .width(size.dp),
                color = Color.Blue,
                strokeWidth = stroke.dp,
            )

            Column(modifier = Modifier.align(Alignment.Center)) {
                Text(
                    text = time,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun CircularProgressIndicatorBackGround(
    modifier: Modifier = Modifier,
    color: Color,
    stroke: Int
) {
    val style = with(LocalDensity.current) { Stroke(stroke.dp.toPx()) }

    Canvas(modifier = modifier, onDraw = {

        val innerRadius = (size.minDimension - style.width)/2

        drawArc(
            color = color,
            startAngle = 0f,
            sweepAngle = 360f,
            topLeft = Offset(
                (size / 2.0f).width - innerRadius,
                (size / 2.0f).height - innerRadius
            ),
            size = Size(innerRadius * 2, innerRadius * 2),
            useCenter = false,
            style = style
        )

    })
}

@Composable
fun CountDownButton(
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    optionSelected: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight())
    {

        Button(
            onClick = {
                optionSelected()
            },
            modifier =
            Modifier
                .height(70.dp)
                .width(200.dp),

            shape = RoundedCornerShape(25.dp),

            )

        {
            val pair = if (!isPlaying) {
                "START"
            } else {
                "STOP"
            }

            Text(
                pair,
                fontSize = 20.sp,
                color = Color.White
            )
        }
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
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CountDownIndicator(
            Modifier.padding(top = 50.dp),
            progress = progress,
            time = time,
            size = 250,
            stroke = 12
        )

        CountDownButton(

            modifier = Modifier
                .padding(top = 70.dp)
                .size(70.dp),
            isPlaying = isPlaying
        ) {
            optionSelected()
        }


    }

}

@Composable
fun CountDownView(viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val time by viewModel.time.observeAsState(Utility.TIME_COUNTDOWN.formatTime())
    val progress by viewModel.progress.observeAsState(1.00F)
    val isPlaying by viewModel.isPlaying.observeAsState(false)
    val celebrate by viewModel.celebrate.observeAsState(false)

    CountDownView(time = time, progress = progress, isPlaying = isPlaying, celebrate = celebrate) {
        viewModel.handleCountDownTimer()
    }

}

@Composable
fun MoneyBar(
    color: Color,
    modifier: Modifier = Modifier
){
    Box(
        modifier = Modifier
            .fillMaxWidth(0.4f)
            .height(40.dp)
            .background(
                color,
                shape = RoundedCornerShape(60.dp)
            ),
        contentAlignment = Alignment.Center
    ){
        Text(text = "Search for music")
    }
}

@ColorInt
fun String.toHslColor(saturation: Float = 0.5f, lightness: Float = 0.4f): Int {
    val hue = fold(0) { acc, char -> char.code + acc * 37 } % 360
    return ColorUtils.HSLToColor(floatArrayOf(hue.absoluteValue.toFloat(), saturation, lightness))
}

@Composable
fun UserHead(
    id: String,
    firstName: String,
    lastName: String,
    modifier: Modifier = Modifier,
    size: Dp = 80.dp,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
) {
    Box(modifier.size(size), contentAlignment = Alignment.Center) {
        val color = remember(id, firstName, lastName) {
            val name = listOf(firstName, lastName)
                .joinToString(separator = "")
                .uppercase()
            Color("$id / $name".toHslColor())
        }
        val initials = (firstName.take(1) + lastName.take(1)).uppercase()
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(SolidColor(color))
            drawCircle(Color.Red, style = Stroke(8f))
        }
        Text(text = initials, style = textStyle, color = Color.White)
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
fun WordDisplay(word: Word, guessedLetters: String) {
    Box(modifier = Modifier
        .padding(2.dp)
        .border(width = 4.dp, color = Gray, shape = RoundedCornerShape(2.dp)),
        contentAlignment = Alignment.TopCenter) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
                .clip(shape = RoundedCornerShape(2.dp)),
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (letter in word.word) {
                    LetterDisplay(letter = letter, shown = !guessedLetters.contains(letter))
                }
            }

            //CategoryDisplay(category = word.category)
        }
    }
        /*
        Box(
            modifier = Modifier
                .size(350.dp)
                .border(width = 4.dp, color = Gray, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Question 1 : How many cars are in the garage?",
                Modifier.padding(16.dp),
                textAlign = TextAlign.Center,
                style = typography.headlineLarge,
            )
        }
        */
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WheelOfFurtuneTheme {
        Greeting("Android")
    }
}