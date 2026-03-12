package com.example.app.storage

import kotlinx.browser.window

actual class LocalStorageManager actual constructor() : StorageManager {

    actual override fun saveItem(item: String, key: String) {
        window.localStorage.setItem(key, item)
    }

    actual override fun getItem(key: String): String {
        return window.localStorage.getItem(key) ?: ""
    }
}
