package com.example.focusmate.data.backup

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import javax.inject.Inject

class JsonExporter @Inject constructor() {

    private val gson: Gson =
        GsonBuilder()
            .setPrettyPrinting()
            .create()

    fun export(
        backupData: BackupData
    ): String {

        return gson.toJson(
            backupData
        )
    }
}
