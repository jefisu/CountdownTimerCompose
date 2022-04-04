package com.jefisu.countdowntimercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jefisu.countdowntimercompose.ui.theme.CountdownTimerComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountdownTimerComposeTheme {
                Surface {
                  Box(
                      modifier = Modifier.fillMaxSize(),
                      contentAlignment = Alignment.Center
                  ) {
                      Timer(time = 5 * 1000L)
                  }
                }
            }
        }
    }
}
