package com.interapptics.composecustomprogressbar

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.interapptics.composecustomprogressbar.ui.theme.Purple40
import com.interapptics.composecustomprogressbar.ui.theme.PurpleGrey40

@Composable
@Preview
fun CustomProgressbar(
  canvasSize: Dp = 300.dp,
  progress: Int = 50,
  maxProgress: Int = 100,
  backgroundIndicatorColor: Color = PurpleGrey40,
  indicatorColor: Color = Purple40,
  indicatorStrokeWidth: Float = 100f,
  bigTextFontSize: TextStyle = MaterialTheme.typography.displaySmall,
  bigTextColor: Color = MaterialTheme.colorScheme.onSurface,
  bigTextSuffix: String = "GB",
  smallText: String = "Remaining",
  smallTextFontSize: TextStyle = MaterialTheme.typography.headlineSmall,
  smallTextColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
) {

  var maxIndicatorValue by remember {
    mutableStateOf(maxProgress)
  }
  maxIndicatorValue = if (progress <= maxProgress) {
    progress
  } else {
    maxProgress
  }
  var animatedIndicatorValue by remember {
    mutableStateOf(0f)
  }

  LaunchedEffect(key1 = maxIndicatorValue) {
    animatedIndicatorValue = maxIndicatorValue.toFloat()
  }

  val percentage = (animatedIndicatorValue / maxProgress) * 100

  val sweepAngle by animateFloatAsState(
    targetValue = (2.4 * percentage).toFloat(),
    animationSpec = tween(durationMillis = 1000)
  )

  val receivedValue by animateIntAsState(
    targetValue = maxIndicatorValue,
    animationSpec = tween(1000)
  )

  val animatedBigTextColor by animateColorAsState(
    targetValue = if (maxIndicatorValue == 0)
      MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
    else
      bigTextColor,
    animationSpec = tween(1000)
  )


  Column(
    modifier = Modifier
      .size(canvasSize)
      .drawBehind {
        val componentSize = size / 1.25f
        backgroundIndicator(
          componentSize = componentSize,
          indicatorColor = backgroundIndicatorColor,
          indicatorStrokeWidth = indicatorStrokeWidth
        )
        foregroundIndicator(
          componentSize = componentSize,
          indicatorColor = indicatorColor,
          indicatorStrokeWidth = indicatorStrokeWidth,
          sweepAngle = sweepAngle
        )
      },
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    EmbeddedElements(
      bigText = receivedValue,
      bigStyle = bigTextFontSize,
      bigTextColor = animatedBigTextColor,
      bigTextSuffix = bigTextSuffix,
      smallText = smallText,
      smallTextColor = smallTextColor,
      smallTextStyle = smallTextFontSize,
    )
  }
}

fun DrawScope.backgroundIndicator(
  componentSize: Size,
  indicatorColor: Color,
  indicatorStrokeWidth: Float
) {
  drawArc(
    size = componentSize,
    color = indicatorColor,
    startAngle = 150f,
    sweepAngle = 240f,
    useCenter = false,
    style = Stroke(
      width = indicatorStrokeWidth,
      cap = StrokeCap.Round
    ),
    topLeft = Offset(
      x = (size.width - componentSize.width) / 2,
      y = (size.height - componentSize.height) / 2,
    )
  )
}

fun DrawScope.foregroundIndicator(
  componentSize: Size,
  indicatorColor: Color,
  indicatorStrokeWidth: Float,
  sweepAngle: Float,
) {
  drawArc(
    size = componentSize,
    color = indicatorColor,
    startAngle = 150f,
    sweepAngle = sweepAngle,
    useCenter = false,
    style = Stroke(
      width = indicatorStrokeWidth,
      cap = StrokeCap.Round
    ),
    topLeft = Offset(
      x = (size.width - componentSize.width) / 2,
      y = (size.height - componentSize.height) / 2,
    )
  )
}

@Composable
fun EmbeddedElements(
  bigText: Int,
  bigStyle: TextStyle,
  bigTextColor: Color,
  bigTextSuffix: String,
  smallText: String,
  smallTextColor: Color,
  smallTextStyle: TextStyle,
) {
  Text(
    text = smallText,
    color = smallTextColor,
    style = bigStyle,
    textAlign = TextAlign.Center
  )
  Text(
    text = "$bigText ${bigTextSuffix.take(2)}",
    color = bigTextColor,
    style = smallTextStyle,
    textAlign = TextAlign.Center,
    fontWeight = FontWeight.Bold
  )
}