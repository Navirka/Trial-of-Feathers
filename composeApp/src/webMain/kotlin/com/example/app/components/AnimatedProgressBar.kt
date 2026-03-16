package com.example.app.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import trialoffeathers.composeapp.generated.resources.Res
import trialoffeathers.composeapp.generated.resources.loading0
import trialoffeathers.composeapp.generated.resources.loading1
import trialoffeathers.composeapp.generated.resources.loading2
import trialoffeathers.composeapp.generated.resources.progress_loading

@Composable
fun AnimatedProgressBar(
    progress: Float,
    modifier: Modifier = Modifier
) {

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 300),

    )

    val currentImage = when {
        animatedProgress <= 0f -> Res.drawable.loading0    // 0%
        animatedProgress <= 0.9f -> Res.drawable.loading1  // 1–90%
        else -> Res.drawable.loading2                       // 90–100%
    }

    Image(
        painter = painterResource(currentImage),
        contentDescription = stringResource(Res.string.progress_loading),
        modifier = modifier
    )
}