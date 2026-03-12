package com.example.app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.example.app.ui.theme.colors.FinishDialogBackground
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import trialoffeathers.composeapp.generated.resources.Res
import trialoffeathers.composeapp.generated.resources.*

@Composable
fun PauseDialog(
    onResume: () -> Unit,
    onExit: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(enabled = false, onClick = {}),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(FinishDialogBackground)
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.pause_text),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .aspectRatio(2.81f),
                    contentScale = ContentScale.Fit
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.6f),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier.wrapContentSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PauseButton(
                        backgroundRes = Res.drawable.button_background,
                        textRes = Res.drawable.resume_text,
                        onClick = onResume,
                        buttonWidthFraction = 0.65f,
                        buttonAspectRatio = 2.47f,
                        textWidthFraction = 0.6f,
                        textAspectRatio = 5.18f
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    PauseButton(
                        backgroundRes = Res.drawable.button_background,
                        textRes = Res.drawable.exit_text,
                        onClick = onExit,
                        buttonWidthFraction = 0.65f,
                        buttonAspectRatio = 2.47f,
                        textWidthFraction = 0.5f,
                        textAspectRatio = 2.83f
                    )
                }
            }
        }
    }
}

@Composable
fun PauseButton(
    backgroundRes: DrawableResource,
    textRes: DrawableResource,
    onClick: () -> Unit,
    buttonWidthFraction: Float,
    buttonAspectRatio: Float,
    textWidthFraction: Float,
    textAspectRatio: Float
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(buttonWidthFraction)
            .aspectRatio(buttonAspectRatio)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(backgroundRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Image(
            painter = painterResource(textRes),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(textWidthFraction)
                .aspectRatio(textAspectRatio),
            contentScale = ContentScale.Fit
        )
    }
}
