package com.syntaxticsugr.callerid.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "callerid")

class DataStorePref(context: Context) {

    private val dataStore = context.dataStore

    suspend fun writeBool(key: String, value: Boolean) {
        val prefKey = booleanPreferencesKey(name = key)

        dataStore.edit { preferences ->
            preferences[prefKey] = value
        }
    }

    suspend fun readBool(key: String, default: Boolean): Boolean {
        val prefKey = booleanPreferencesKey(name = key)

        return try {
            val preferences = dataStore.data.first()
            preferences[prefKey] ?: default
        } catch (e: IOException) {
            default
        }
    }

}
