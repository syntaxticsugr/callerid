package com.syntaxticsugr.callerid.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

private val Context.dataStore by preferencesDataStore(name = "callerid")

class DataStorePref(context: Context) {

    private val dataStore = context.dataStore

    suspend fun writeBool(key: String, value: Boolean) {
        val prefKey = booleanPreferencesKey(name = key)

        dataStore.edit { preferences ->
            preferences[prefKey] = value
        }
    }

    suspend fun readBool(key: String, default: Boolean): Boolean {
        return withContext(Dispatchers.IO) {
            val prefKey = booleanPreferencesKey(name = key)

            val preferences = dataStore.data.first()
            preferences[prefKey] ?: default
        }
    }

    suspend fun writeString(key: String, value: String) {
        val prefKey = stringPreferencesKey(name = key)

        dataStore.edit { preferences ->
            preferences[prefKey] = value
        }
    }

    suspend fun readString(key: String, default: String): String {
        return withContext(Dispatchers.IO) {
            val prefKey = stringPreferencesKey(name = key)

            val preferences = dataStore.data.first()
            preferences[prefKey] ?: default
        }
    }

}
