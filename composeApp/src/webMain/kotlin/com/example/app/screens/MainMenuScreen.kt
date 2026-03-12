package com.example.app.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.app.ui.AppBackground
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import trialoffeathers.composeapp.generated.resources.Res
import trialoffeathers.composeapp.generated.resources.*

@Composable
fun MainMenuScreen(
    onNavigateToGame: () -> Unit,
    onNavigateToRecords: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // ВЕРТИКАЛЬНАЯ КОЛОНКА С WEIGHT
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ВЕРХНИЙ ОТСТУП (заменяет padding top = 50.dp)
            Spacer(modifier = Modifier.weight(0.2f))

            // ЛОГОТИП
            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.67f)
                    .aspectRatio(0.72f)
            )

            // ГИБКОЕ ПРОСТРАНСТВО МЕЖДУ ЛОГОТИПОМ И КНОПКАМИ
            Spacer(modifier = Modifier.weight(0.4f))

            // КНОПКИ МЕНЮ
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // КНОПКА "START"
                MenuButton(
                    backgroundImageId = Res.drawable.button_background,
                    textImageId = Res.drawable.start,
                    onClick = onNavigateToGame,
                    buttonWidthFraction = 0.655f,
                    buttonAspectRatio = 2.477f,
                    textWidthFraction = 0.459f,
                    textAspectRatio = 2.818f
                )

                // КНОПКА "RECORDS"
                MenuButton(
                    backgroundImageId = Res.drawable.button_background,
                    textImageId = Res.drawable.records,
                    onClick = onNavigateToRecords,
                    buttonWidthFraction = 0.655f,
                    buttonAspectRatio = 2.477f,
                    textWidthFraction = 0.585f,
                    textAspectRatio = 3.591f
                )
            }

            // НИЖНИЙ ОТСТУП (заменяет padding bottom = 60.dp)
            Spacer(modifier = Modifier.weight(0.25f))
        }
    }
}

@Composable
fun MenuButton(
    backgroundImageId: DrawableResource,
    textImageId: DrawableResource,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonWidthFraction: Float,
    buttonAspectRatio: Float,
    textWidthFraction: Float,
    textAspectRatio: Float
) {
    Box(
        modifier = modifier
            .fillMaxWidth(buttonWidthFraction)
            .aspectRatio(buttonAspectRatio)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(backgroundImageId),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Image(
            painter = painterResource(textImageId),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(textWidthFraction)
                .aspectRatio(textAspectRatio),
            contentScale = ContentScale.Fit
        )
    }
}