package com.example.app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.example.app.ui.theme.AppTextStyles
import com.example.app.ui.theme.colors.FinishDialogBackground
import org.jetbrains.compose.resources.painterResource
import trialoffeathers.composeapp.generated.resources.Res
import trialoffeathers.composeapp.generated.resources.*
import kotlin.time.Clock

@Composable
fun FinishDialog(
    finalScore: Int,
    onNavigateToMenu: () -> Unit,
    onPlayAgain: () -> Unit
) {
    val isWin = finalScore >= 0
    val dateString = epochMillisToDateString(Clock.System.now().toEpochMilliseconds())

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

        val windowWidth = with(LocalDensity.current) {
            LocalWindowInfo.current.containerSize.width.toDp()
        }
        val topPadding = 0.121f * windowWidth

        BackButton(
            onClick = onNavigateToMenu,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = topPadding, start = 32.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(0.1f))

            if (isWin) {
                Image(
                    painter = painterResource(Res.drawable.you_won_text),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.66f)
                        .aspectRatio(4.2f),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.weight(0.05f))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.77f)
                        .aspectRatio(2.79f),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.button_background),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedText(
                            text = dateString,
                            textStyle = AppTextStyles.labelSmall.copy(fontSize = 28.sp),
                            textAlign = TextAlign.Start
                        )

                        OutlinedText(
                            text = "+${finalScore}",
                            textStyle = AppTextStyles.labelSmall.copy(fontSize = 28.sp),
                            textAlign = TextAlign.End
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(0.05f))

            } else {
                Image(
                    painter = painterResource(Res.drawable.game_over_text),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .aspectRatio(2.24f),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.weight(0.05f))
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .aspectRatio(2.47f)
                    .clickable(onClick = onPlayAgain),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.button_background),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )

                Image(
                    painter = painterResource(Res.drawable.play_again_text),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.42f)
                        .aspectRatio(1.91f),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.weight(0.3f))
        }
    }
}

// Gregorian calendar algorithm — no kotlinx-datetime Instant needed
internal fun epochMillisToDateString(millis: Long): String {
    val days = (millis / 86400000).toInt()
    val z = days + 719468
    val era = (if (z >= 0) z else z - 146096) / 146097
    val doe = z - era * 146097
    val yoe = (doe - doe / 1460 + doe / 36524 - doe / 146096) / 365
    val doy = doe - (365 * yoe + yoe / 4 - yoe / 100)
    val mp = (5 * doy + 2) / 153
    val day = doy - (153 * mp + 2) / 5 + 1
    val month = if (mp < 10) mp + 3 else mp - 9
    return "${day.toString().padStart(2, '0')}.${month.toString().padStart(2, '0')}"
}
