package com.jefisu.countdowntimercompose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Timer(
    time: Long,
    inactiveColor: Color = Color.DarkGray,
    activeColor: Color = Color(0xFF37B900),
    strokeWidth: Dp = 8.dp,
    boxSize: Dp = 200.dp
) {
    var currentTime by remember { mutableStateOf(time) }
    var isTimerRunning by remember { mutableStateOf(false) }
    var fillPercentage by remember { mutableStateOf(1F) }

    LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
        if (currentTime > 0 && isTimerRunning) {
            delay(100)
            currentTime -= 100
            fillPercentage = currentTime / time.toFloat()
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(boxSize)
            .drawBehind {
                drawArc(
                    color = inactiveColor,
                    startAngle = -215F,
                    sweepAngle = 250F,
                    useCenter = false,
                    style = Stroke(
                        width = strokeWidth.toPx(),
                        cap = StrokeCap.Round
                    )
                )
                drawArc(
                    color = activeColor,
                    startAngle = -215F,
                    sweepAngle = 250F * fillPercentage,
                    useCenter = false,
                    style = Stroke(
                        width = strokeWidth.toPx(),
                        cap = StrokeCap.Round
                    )
                )
                val center = Offset(size.width / 2f, size.height / 2f)
                val beta = (250f * fillPercentage + 145f) * (PI / 180f).toFloat()
                val r = size.width / 2f
                val a = cos(beta) * r
                val b = sin(beta) * r
                drawPoints(
                    points = listOf(Offset(center.x + a, center.y + b)),
                    pointMode = PointMode.Points,
                    color = activeColor,
                    strokeWidth = (strokeWidth * 3F).toPx(),
                    cap = StrokeCap.Round
                )
            }
    ) {
        Text(
            text = toTimeFormat(currentTime),
            style = MaterialTheme.typography.h4
        )
        Button(
            modifier = Modifier.align(Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (!isTimerRunning || currentTime == 0L) activeColor else Color.Red
            ),
            onClick = {
                if (currentTime == 0L) {
                    currentTime = time
                    fillPercentage = currentTime / time.toFloat()
                    isTimerRunning = false
                } else {
                    isTimerRunning = !isTimerRunning
                }
            }
        ) {
            Text(
                text = when {
                    !isTimerRunning && currentTime != time && currentTime > 0L -> "resume"
                    isTimerRunning && currentTime > 0L -> "pause"
                    !isTimerRunning && currentTime > 0L -> "start"
                    else -> "restart"
                }.uppercase()
            )
        }
    }
}