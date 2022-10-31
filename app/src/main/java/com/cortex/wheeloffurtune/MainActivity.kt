package com.cortex.wheeloffurtune

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.ColorInt
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.cortex.wheeloffurtune.ui.theme.WheelOfFurtuneTheme
import com.cortex.wheeloffurtune.view.CountDownView
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
                        Box(modifier = Modifier.fillMaxSize(0.8f),
                        contentAlignment = Alignment.TopCenter) {
                            CategoryDisplay(category = word.category, backgroundGradientSide = Color(getColor(R.color.teal_700)), backgroundGradientMiddle = Color(getColor(R.color.teal_200)), borderGradientSide = Color(getColor(R.color.purple_500)), borderGradientMiddle = Color(getColor(R.color.purple_200)))
                            CountDownView()
                        }
                    }
                }
            }
        }
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
        .padding(top = 10.dp)
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