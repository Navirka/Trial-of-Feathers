package com.example.app.screens.records

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.model.GameRecord
import com.example.app.storage.LocalStorageManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.json.Json

private const val RECORDS_KEY = "game_records"

class RecordsViewModel() : ViewModel() {

    private val localStorageManager = LocalStorageManager()

    val sortedResults: StateFlow<List<GameRecord>> = flow {
        val json = localStorageManager.getItem(RECORDS_KEY)
        val records = if (json.isEmpty()) {
            emptyList()
        } else {
            Json.decodeFromString<List<GameRecord>>(json)
        }
        emit(records.sortedByDescending { it.score })
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
}
