package com.syntaxticsugr.callerid.utils

import android.content.Context
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

    fun saveAuthKey(context: Context, authKey: String) {
        val authFile = File(context.filesDir, AUTH_KEY_FILE_NAME)
        authFile.writeText(authKey)
    }
}
