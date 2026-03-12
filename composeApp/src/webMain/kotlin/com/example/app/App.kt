package com.example.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.app.di.appModule
import com.example.app.navigation.AppNavGraph
import org.koin.compose.KoinApplication

@Composable
fun App() {
    KoinApplication(application = {
        modules(appModule)
    }) {
        val navController = rememberNavController()
        AppNavGraph(navController = navController)
    }
}
