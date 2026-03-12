package com.example.app.di

import com.example.app.screens.game.GameViewModel
import com.example.app.screens.records.RecordsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { GameViewModel() }
    viewModel { RecordsViewModel() }
}
