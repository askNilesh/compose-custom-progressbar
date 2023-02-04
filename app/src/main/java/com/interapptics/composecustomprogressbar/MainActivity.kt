package com.interapptics.composecustomprogressbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.interapptics.composecustomprogressbar.ui.theme.ComposeCustomProgressbarTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      ComposeCustomProgressbarTheme {
        // A surface container using the 'background' color from the theme
        val sliderPositionState = remember {
          mutableStateOf(0f)
        }
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Box(modifier = Modifier.size(300.dp)) {
              CustomProgressbar(
                progress = sliderPositionState.value.toInt()
              )
            }
            Slider(value = sliderPositionState.value,
              valueRange = 0f..100f,
              modifier = Modifier.padding(start = 30.dp, end = 30.dp),
              onValueChange = { newVal ->
                sliderPositionState.value = newVal

              })
          }
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  ComposeCustomProgressbarTheme {
    CustomProgressbar()
  }
}