package com.example.app.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Menu : Screen("menu")
    object Game : Screen("game")
    object Records : Screen("records")
}
