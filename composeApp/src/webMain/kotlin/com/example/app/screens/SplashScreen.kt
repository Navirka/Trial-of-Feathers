package com.example.app.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.example.app.components.AnimatedProgressBar
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import trialoffeathers.composeapp.generated.resources.Res
import trialoffeathers.composeapp.generated.resources.background
import trialoffeathers.composeapp.generated.resources.loading_bg
import trialoffeathers.composeapp.generated.resources.logo

@Composable
fun SplashScreen(
    onNavigateToMenu: () -> Unit
) {
    var progress by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        animateProgress(from = 0f, to = 0.5f, duration = 1000L) { value ->
            progress = value
        }

        animateProgress(from = 0.5f, to = 0.65f, duration = 500L) { value ->
            progress = value
        }

        animateProgress(from = 0.65f, to = 0.85f, duration = 800L) { value ->
            progress = value
        }
        delay(300)

        onNavigateToMenu()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val logoSize = 320f / 444f
        val barSize = 340f / 36f

        Image(
            painter = painterResource(Res.drawable.loading_bg),
            contentDescription = stringResource(Res.string.background),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Spacer(modifier = Modifier.weight(0.2f))

            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = stringResource(Res.string.logo),
                modifier = Modifier
                    .fillMaxWidth(0.77f)
                    .aspectRatio(logoSize)
            )

            Spacer(modifier = Modifier.weight(0.3f))

            AnimatedProgressBar(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth(0.82f)
                    .aspectRatio(barSize)
            )

            Spacer(modifier = Modifier.weight(0.3f))
        }
    }
}

private suspend fun animateProgress(
    from: Float,
    to: Float,
    duration: Long,
    onProgressUpdate: (Float) -> Unit
) {
    val steps = 60
    val stepDuration = duration / steps
    val stepValue = (to - from) / steps

    repeat(steps) {
        delay(stepDuration)
        val currentValue = from + (stepValue * (it + 1))
        onProgressUpdate(currentValue)
    }
}
