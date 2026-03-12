package com.example.app.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.app.screens.MainMenuScreen
import com.example.app.screens.SplashScreen
import com.example.app.screens.game.GameScreen
import com.example.app.screens.records.RecordsScreen
import com.example.app.ui.AppBackground

@Composable
fun AppNavGraph(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {

        AppBackground()

        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route
        ) {
            composable(Screen.Splash.route) {
                SplashScreen(
                    onNavigateToMenu = {
                        navController.navigateSafely(Screen.Menu.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Menu.route) {
                MainMenuScreen(
                    onNavigateToGame = {
                        navController.navigateSafely(Screen.Game.route)
                    },
                    onNavigateToRecords = {
                        navController.navigateSafely(Screen.Records.route)
                    }
                )
            }

            composable(Screen.Game.route) {
                GameScreen(
                    onExitToMenu = {
                        navController.navigateUpSafely()
                    }
                )
            }

            composable(Screen.Records.route) {
                RecordsScreen(
                    onNavigateBack = {
                        navController.navigateUpSafely()
                    }
                )
            }
        }
    }
}
