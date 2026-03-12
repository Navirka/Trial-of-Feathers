package com.example.app.navigation

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder

fun NavController.navigateSafely(
    route: String,
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    currentBackStackEntry?.let { backEntry ->
        if (backEntry.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            navigate(route, builder)
        }
    }
}

fun NavController.navigateUpSafely() {
    currentBackStackEntry?.let { backEntry ->
        if (backEntry.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            navigateUp()
        }
    }
}