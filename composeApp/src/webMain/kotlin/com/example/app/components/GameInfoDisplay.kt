package com.example.app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app.ui.theme.AppTextStyles
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import trialoffeathers.composeapp.generated.resources.Res
import trialoffeathers.composeapp.generated.resources.back_counter
import trialoffeathers.composeapp.generated.resources.button_background

@Composable
fun GameInfoDisplay(
    value: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.back_counter),
            contentDescription = stringResource(Res.string.button_background),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        OutlinedText(
            text = "$value",
            textStyle = AppTextStyles.labelSmall.copy(fontSize = 28.sp),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}
