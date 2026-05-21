package com.example.focusmate.data.backup

import com.google.gson.Gson
import javax.inject.Inject

class JsonImporter @Inject constructor() {

    private val gson: Gson =
        Gson()

    fun import(
        json: String
    ): BackupData {

        return gson.fromJson(
            json,
            BackupData::class.java
        )
    }
}
