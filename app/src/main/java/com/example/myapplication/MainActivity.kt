package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    App()
                }
            }
        }
    }
}

fun DrawScope.circle(offset: (Float) -> Offset) {
    val radius = Dp(20f).value;
    drawCircle(
        Color.Black,
        radius = radius,
        center = offset(radius)
    )
}

fun DrawScope.center() {
    circle() {
        Offset((size.width / 2f) + (it / 2f), (size.height / 2f) + (it / 2f))
    }
}

fun DrawScope.topRight() {
    circle {
        Offset(size.width - it, it * 2f)
    }
}

fun DrawScope.topLeft() {
    circle {
        Offset(it * 2f, it * 2f)
    }
}

fun DrawScope.leftBottom() {
    circle {
        Offset(it * 2f, size.height - it)
    }
}

fun DrawScope.rightBottom() {
    circle {
        Offset(size.width - it, size.height - it)
    }
}

fun DrawScope.rightCenter() {
    circle {
        Offset(size.width - it, (size.height / 2f) + (it / 2f))
    }
}

fun DrawScope.leftCenter() {
    circle {
        Offset(it * 2f, (size.height / 2f) + (it / 2f))
    }
}

fun DrawScope.bullet(number: Int) {
    when (number) {
        1 -> {
            center()
        }
        2 -> {
            topRight()
            leftBottom()
        }
        3 -> {
            center()
            topRight()
            leftBottom()
        }
        4 -> {
            topRight()
            topLeft()
            rightBottom()
            leftBottom()
        }
        5 -> {
            center()
            topRight()
            topLeft()
            rightBottom()
            leftBottom()
        }
        6 -> {
            rightCenter()
            leftCenter()
            topRight()
            topLeft()
            rightBottom()
            leftBottom()
        }
    }
}

fun isNumeric(toCheck: String): Boolean {
    val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
    return toCheck.matches(regex)
}

@Composable
fun Dice(digitNumber: Int, modifier: Modifier) {
    Canvas(modifier = modifier.size(96.dp, 96.dp)) {
        drawRoundRect(
            Color.Green,
            topLeft = Offset(10f, 10f),
            size = size,
            cornerRadius = CornerRadius(20f, 20f)
        )
        bullet(number = digitNumber)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {

    val context = LocalContext.current

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
    ) {
        var diceGuessState by remember { mutableStateOf(0) }
        var guess by remember { mutableStateOf("0") }

        Dice(diceGuessState, modifier = Modifier.align(Alignment.Center).offset(y = (-250).dp))

        TextField(
            value = guess,
            onValueChange = {
                guess = it
            },
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (60).dp),
            label = {Text("Escolha o dado de 1 a 6")}
        )
        Button(
            onClick = {
                      if (guess.isBlank()) {
                          Toast.makeText(context, "Escolha um número válido", Toast.LENGTH_SHORT).show()
                      } else {
                          if (isNumeric(guess)) {
                              try {
                                  diceGuessState = guess.toInt()
                              } catch (e: Exception) {
                                  Toast.makeText(context, "Escolha um número inteiro válido", Toast.LENGTH_SHORT).show()
                              }
                          } else {
                              Toast.makeText(context, "Escolha um número válido", Toast.LENGTH_SHORT).show()
                          }
                      }
            },
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (120).dp)
        ) {
            Text("Jogar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            App()
        }
    }
}