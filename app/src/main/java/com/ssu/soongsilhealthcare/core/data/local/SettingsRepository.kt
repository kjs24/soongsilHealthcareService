package com.ssu.soongsilhealthcare.core.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.ssu.soongsilhealthcare.core.model.AppSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.settingsDataStore by preferencesDataStore(name = "app_settings")

class SettingsRepository(context: Context) {
    private val dataStore = context.applicationContext.settingsDataStore

    val settings: Flow<AppSettings> = dataStore.data.map { preferences ->
        AppSettings(
            darkMode = preferences[DARK_MODE] ?: false,
            notificationEnabled = preferences[NOTIFICATION_ENABLED] ?: false
        )
    }

    suspend fun setDarkMode(enabled: Boolean) {
        dataStore.edit { it[DARK_MODE] = enabled }
    }

    suspend fun setNotificationEnabled(enabled: Boolean) {
        dataStore.edit { it[NOTIFICATION_ENABLED] = enabled }
    }

    private companion object {
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val NOTIFICATION_ENABLED = booleanPreferencesKey("notification_enabled")
    }
}
