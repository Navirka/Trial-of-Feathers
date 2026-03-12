package com.example.app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import trialoffeathers.composeapp.generated.resources.Res
import trialoffeathers.composeapp.generated.resources.*

@Composable
fun AppBackground() {
    Image(
        painter = painterResource(Res.drawable.background),
        contentDescription = stringResource(Res.string.menu_background),
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}