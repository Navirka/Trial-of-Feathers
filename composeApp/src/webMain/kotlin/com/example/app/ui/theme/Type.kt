package com.example.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import trialoffeathers.composeapp.generated.resources.Res
import trialoffeathers.composeapp.generated.resources.inter_one

val inter: FontFamily
    @Composable get() = FontFamily(Font(Res.font.inter_one, FontWeight.Normal))

val AppTypography: Typography
    @Composable get() = Typography(
        titleLarge = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            letterSpacing = 0.sp,
            textAlign = TextAlign.Center
        )
    )

object AppTextStyles {
    val labelLarge: TextStyle
        @Composable get() = TextStyle(
            fontSize = 36.sp,
            fontFamily = inter,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

    val labelMedium: TextStyle
        @Composable get() = TextStyle(
            fontSize = 32.sp,
            fontFamily = inter,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

    val labelSmall: TextStyle
        @Composable get() = TextStyle(
            fontSize = 32.sp,
            fontFamily = inter,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
}
