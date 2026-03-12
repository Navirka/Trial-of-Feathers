package com.example.app.storage

expect class LocalStorageManager() : StorageManager {
    override fun saveItem(item: String, key: String)
    override fun getItem(key: String): String
}
