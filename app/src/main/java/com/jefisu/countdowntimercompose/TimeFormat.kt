package com.jefisu.countdowntimercompose

import java.text.SimpleDateFormat
import java.util.*

fun toTimeFormat(time: Long): String {
    val pattern = SimpleDateFormat("mm:ss", Locale.getDefault())
    return pattern.format(time)
}