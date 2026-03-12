package com.example.app.screens.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.app.components.FinishDialog
import com.example.app.components.GameInfoDisplay
import com.example.app.components.PauseDialog
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import trialoffeathers.composeapp.generated.resources.Res
import trialoffeathers.composeapp.generated.resources.*

@Composable
fun GameScreen(
    viewModel: GameViewModel = koinViewModel(),
    onExitToMenu: () -> Unit
) {
    val balance by viewModel.balance.collectAsStateWithLifecycle()
    val spinsLeft by viewModel.spinsLeft.collectAsStateWithLifecycle()
    val isSpinning by viewModel.isSpinning.collectAsStateWithLifecycle()
    val wheelRotation by viewModel.wheelRotation.collectAsStateWithLifecycle()
    val gameState by viewModel.gameState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {

        val windowWidth = with(LocalDensity.current) {
            LocalWindowInfo.current.containerSize.width.toDp()
        }
        val topPadding = 0.135f * windowWidth

        // ВЕРХНІЙ HUD
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = topPadding, start = 24.dp, end = 24.dp)
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ПАУЗА
            Image(
                painter = painterResource(Res.drawable.pause),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.12f)
                    .aspectRatio(1f)
                    .clickable { viewModel.togglePause() }
            )

            // БАЛАНС
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.35f)
                    .aspectRatio(2.06f),
                contentAlignment = Alignment.Center
            ) {
                GameInfoDisplay(
                    value = balance,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // СПРОБИ
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.35f)
                    .aspectRatio(2.06f),
                contentAlignment = Alignment.Center
            ) {
                GameInfoDisplay(
                    value = spinsLeft,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        // ЦЕНТР ГАРИ
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(0.1f))

            // СТРІЛКА
            Image(
                painter = painterResource(Res.drawable.reel),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.22f)
                    .aspectRatio(1f)
                    .offset(y = 13.dp)
                    .zIndex(1f)
            )

            // КОЛЕСО
            Image(
                painter = painterResource(Res.drawable.wheel),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.81f)
                    .aspectRatio(1f)
                    .rotate(-wheelRotation),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.weight(0.05f))

            // КНОПКА START
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .aspectRatio(2.47f)
                    .clickable(
                        enabled = !isSpinning && spinsLeft > 0,
                        onClick = { viewModel.startSpin() }
                    )
                    .alpha(if (!isSpinning && spinsLeft > 0) 1f else 0.5f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.button_background),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )

                Image(
                    painter = painterResource(Res.drawable.start),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.45f)
                        .aspectRatio(124f / 44f),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.weight(0.2f))
        }

        when (gameState) {
            GameViewModel.GameState.PAUSED -> {
                PauseDialog(
                    onResume = { viewModel.togglePause() },
                    onExit = {
                        viewModel.exitGame()
                        onExitToMenu()
                    }
                )
            }
            GameViewModel.GameState.FINISHED -> {
                FinishDialog(
                    finalScore = balance,
                    onNavigateToMenu = onExitToMenu,
                    onPlayAgain = { viewModel.restartGame() }
                )
            }
            else -> {}
        }
    }
}
