package com.example.app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import trialoffeathers.composeapp.generated.resources.Res
import trialoffeathers.composeapp.generated.resources.*

@Composable
fun BackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {


    Image(
        painter = painterResource(Res.drawable.arrow_back),
        contentDescription = stringResource(Res.string.back_button),
        modifier = modifier
            .fillMaxWidth(0.12f)
            .aspectRatio(1f)
            .clickable(
                onClick = onClick
            ),
        contentScale = ContentScale.Fit
    )
}