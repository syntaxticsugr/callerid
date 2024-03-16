package com.syntaxticsugr.tcaller.utils

import android.content.Context
import org.json.JSONObject
import java.io.File

object AuthKeyManager {
    private const val AUTH_KEY_FILE_NAME = "auth.key"

    fun getAuthKey(context: Context): String? {
        val authFile = File(context.filesDir, AUTH_KEY_FILE_NAME)
        if (authFile.exists()) {
            return authFile.readText()
        }
        return null
    }

    fun saveAuthKey(context: Context, response: JSONObject) {
        val authFile = File(context.filesDir, AUTH_KEY_FILE_NAME)

        val installationId = response.getString("installationId")

        authFile.writeText(installationId)
    }
}
