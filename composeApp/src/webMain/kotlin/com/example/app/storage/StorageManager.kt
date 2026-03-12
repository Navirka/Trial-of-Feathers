package com.example.app.storage

interface StorageManager {
    fun saveItem(item: String, key: String)
    fun getItem(key: String): String
}