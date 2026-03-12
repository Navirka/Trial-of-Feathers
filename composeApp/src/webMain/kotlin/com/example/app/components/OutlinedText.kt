package com.example.app.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.app.ui.theme.AppTextStyles
import com.example.app.ui.theme.colors.OutlinedTextFill
import com.example.app.ui.theme.colors.OutlinedTextOutline
@Composable
fun OutlinedText(
    text: String,
    modifier: Modifier = Modifier,
    fillColor: Color = OutlinedTextFill,
    outlineColor: Color = OutlinedTextOutline,
    textStyle: TextStyle = AppTextStyles.labelLarge,
    outlineWidth: Float = 7f,
    textAlign: TextAlign = TextAlign.Center,
    letterSpacing: Float = 1f
) {

    val stableTextStyle = remember(textStyle, letterSpacing) {
        textStyle.copy(
            letterSpacing = letterSpacing.sp
        )
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // Контур
        Text(
            text = text,
            color = outlineColor,
            style = stableTextStyle.copy(
                drawStyle = Stroke(width = outlineWidth)
            ),
            textAlign = textAlign
        )

        // Заполнение
        Text(
            text = text,
            color = fillColor,
            style = stableTextStyle,
            textAlign = textAlign
        )
    }
}