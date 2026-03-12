package com.example.app.model

import kotlinx.serialization.Serializable

@Serializable
data class GameRecord(val id: Long, val score: Int, val date: Long)