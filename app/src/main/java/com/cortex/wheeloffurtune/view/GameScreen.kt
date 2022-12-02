package com.cortex.wheeloffurtune.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.darkColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cortex.wheeloffurtune.ui.theme.WheelOfFurtuneTheme
import com.cortex.wheeloffurtune.viewmodel.*
import com.cortex.wheeloffurtune.viewmodel.GameUiViewModel.Companion.extendWordToSize
import com.cortex.wheeloffurtune.viewmodel.GameUiViewModel.Companion.replaceUnderscoresWithSpace

@Composable
fun Grid(
    gameUiViewModel: GameUiViewModel,
    word: List<Char>) {
    val uiState = gameUiViewModel.uiState.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(17),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 2.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(word) { char ->
            LetterDisplay(letter = char, shown = uiState.value.guessedLetters.contains(char.uppercaseChar()))
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
            .border(width = 2.dp, color = Color.Gray, shape = RoundedCornerShape(4.dp)),
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
fun UserHead(
    firstName: String,
    lastName: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = darkColors().primary,
    strokeColor: Color = darkColors().primaryVariant
) {
    Box(modifier, contentAlignment = Alignment.Center) {
        val initials = (firstName.take(1) + lastName.take(1)).uppercase()

        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(SolidColor(backgroundColor))
            drawCircle(strokeColor, style = Stroke(8f))
        }

        Text(text = initials,
            style = MaterialTheme.typography.titleLarge,
            color = darkColors().onPrimary
        )
    }
}

@Composable
fun MoneyBar(
    gameUiViewModel: GameUiViewModel,
    modifier: Modifier = Modifier,
    color: Color = darkColors().primary
){
    val uiState = gameUiViewModel.uiState.collectAsState()

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
        Text(text = uiState.value.money.toString() + "$",
            style = MaterialTheme.typography.titleLarge,
            color = darkColors().onPrimary
        )
    }
}

@Composable
fun WordDisplay(modifier: Modifier = Modifier,
                gameUiViewModel: GameUiViewModel) {
    val uiState = gameUiViewModel.uiState.collectAsState()

    Column(modifier = Modifier
        .background(darkColors().background)) {
        Grid(gameUiViewModel = gameUiViewModel, word = extendWordToSize("", 17).toList())
        Grid(gameUiViewModel = gameUiViewModel, word = extendWordToSize(uiState.value.word, 17).toList())
        Grid(gameUiViewModel = gameUiViewModel, word = extendWordToSize("", 17).toList())
    }
}

@Composable
fun HealthBar(
    gameUiViewModel: GameUiViewModel,
    modifier: Modifier = Modifier,
    color: Color = darkColors().primary
){
    val uiState = gameUiViewModel.uiState.collectAsState()
    val iconsIndex = (0 until uiState.value.lives).toList()
    val icons = List(uiState.value.lives) { index -> Icons.Default.Favorite }

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
        LazyRow {
            items(iconsIndex) {
                Icon(imageVector = icons[it], contentDescription = "Heart", tint = darkColors().onPrimary)
            }
        }

    }
}


@Composable
fun Game(navController: NavController, gameUiViewModel: GameUiViewModel, keyboardViewModel: KeyboardViewModel, wheelViewModel: WheelViewModel, countDownViewModel: CountDownViewModel, modifier: Modifier = Modifier) {
    WheelOfFurtuneTheme {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = darkColors().background,
        ) {
            val keyboardUiState = keyboardViewModel.uiState.collectAsState()
            val gameUiState = gameUiViewModel.uiState.collectAsState()
            val wheelUiState = wheelViewModel.uiState.collectAsState()

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                UserHead(firstName = "Lucas",
                    lastName = "Eiruff",
                    modifier = Modifier.size(80.dp))

                Row {
                    HealthBar(gameUiViewModel = gameUiViewModel)
                    MoneyBar(gameUiViewModel = gameUiViewModel)
                }

                WordDisplay(gameUiViewModel = gameUiViewModel)

                Spacer(Modifier.size(10.dp))

                Box(modifier = Modifier.fillMaxWidth(0.8f),
                    contentAlignment = Alignment.TopCenter) {
                    CategoryDisplay(category = gameUiState.value.category,
                        backgroundGradientSide = darkColors().primary,
                        backgroundGradientMiddle = darkColors().secondary,
                        borderGradientSide = darkColors().primaryVariant,
                        borderGradientMiddle = darkColors().secondaryVariant
                    )
                    CountDownView(countDownViewModel)
                }

                KeyboardScreen(keyboardViewModel = keyboardViewModel, gameUiViewModel = gameUiViewModel, countDownViewModel = countDownViewModel, currentReward = wheelViewModel.getWheelResultAsInt(wheelUiState.value.wheelResult), navController = navController)

                Spacer(modifier = Modifier
                    .size(30.dp)
                    .fillMaxWidth())

                Box(modifier = Modifier.fillMaxSize()) {
                    SpinningWheel(wheelViewModel = wheelViewModel)
                    CircularWheelButton(
                        onClick = {
                            wheelViewModel.spin()
                            if (wheelViewModel.uiState.value.wheelResult == WheelResult.BANKRUPT) {
                                gameUiViewModel.resetMoney()
                            } else {
                                gameUiViewModel.waitForGuess()
                            }
                        },
                        shown = gameUiState.value.gameState == GameState.WAITING_FOR_SPIN,
                        modifier = Modifier.padding(start = 15.dp))
                }
            }
        }
    }
}