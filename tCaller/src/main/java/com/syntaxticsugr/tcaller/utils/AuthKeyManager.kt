package com.syntaxticsugr.tcaller.utils

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

    fun saveAuthKey(context: Context, installationId: String) {
        val authFile = File(context.filesDir, AUTH_KEY_FILE_NAME)

        authFile.writeText(installationId)
    }

}
