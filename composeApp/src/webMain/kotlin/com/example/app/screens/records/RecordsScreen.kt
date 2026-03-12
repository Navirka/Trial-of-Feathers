package com.example.app.screens.records

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app.components.BackButton
import com.example.app.components.OutlinedText
import com.example.app.model.GameRecord
import com.example.app.ui.theme.AppTextStyles
import com.example.app.components.epochMillisToDateString
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import trialoffeathers.composeapp.generated.resources.Res
import trialoffeathers.composeapp.generated.resources.button_background
import trialoffeathers.composeapp.generated.resources.no_rec
import trialoffeathers.composeapp.generated.resources.records_text

@Composable
fun RecordsScreen(
    viewModel: RecordsViewModel = koinViewModel(),
    onNavigateBack: () -> Unit
) {
    val formattedResults by viewModel.sortedResults.collectAsState()
    val isEmpty = formattedResults.isEmpty()

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val topPadding = maxHeight * 0.07f

        BackButton(
            onClick = onNavigateBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = topPadding, start = 32.dp)
        )

        Image(
            painter = painterResource(Res.drawable.records_text),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = topPadding)
                .fillMaxWidth(0.44f)
                .aspectRatio(3.57f),
            contentScale = ContentScale.Fit
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .padding(bottom = 30.dp)
                .align(Alignment.BottomCenter),
            contentAlignment = Alignment.Center
        ) {
            if (isEmpty) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Image(
                        painter = painterResource(Res.drawable.no_rec),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(0.83f)
                            .aspectRatio(6.61f),
                        contentScale = ContentScale.Fit
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(formattedResults) { result ->
                        SingleResultItem(
                            result = result,
                            modifier = Modifier.fillMaxWidth(0.85f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SingleResultItem(
    result: GameRecord,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.button_background),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2.79f)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.FillBounds
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val dateText = epochMillisToDateString(result.date)

            OutlinedText(
                text = dateText,
                textStyle = AppTextStyles.labelSmall.copy(fontSize = 28.sp),
                textAlign = TextAlign.Start
            )

            val scoreText = if (result.score >= 0) "+${result.score}" else "${result.score}"

            OutlinedText(
                text = scoreText,
                textStyle = AppTextStyles.labelSmall.copy(fontSize = 28.sp),
                textAlign = TextAlign.End
            )
        }
    }
}
