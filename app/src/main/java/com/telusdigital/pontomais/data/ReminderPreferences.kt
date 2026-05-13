package com.telusdigital.pontomais.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.telusdigital.pontomais.reminder.ReminderConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.reminderDataStore: DataStore<Preferences> by preferencesDataStore(name = "reminder_prefs")

class ReminderPreferences(private val appContext: Context) {

    val config: Flow<ReminderConfig> = appContext.reminderDataStore.data.map { prefs ->
        ReminderConfig(
            enabled = prefs[KEY_ENABLED] ?: false,
            hour    = prefs[KEY_HOUR] ?: 9,
            minute  = prefs[KEY_MINUTE] ?: 0,
            leadMin = prefs[KEY_LEAD] ?: 5,
        )
    }

    suspend fun save(config: ReminderConfig) {
        appContext.reminderDataStore.edit { prefs ->
            prefs[KEY_ENABLED] = config.enabled
            prefs[KEY_HOUR]    = config.hour.coerceIn(0, 23)
            prefs[KEY_MINUTE]  = config.minute.coerceIn(0, 59)
            prefs[KEY_LEAD]    = config.leadMin
        }
    }

    companion object {
        private val KEY_ENABLED = booleanPreferencesKey("reminder_enabled")
        private val KEY_HOUR    = intPreferencesKey("reminder_hour")
        private val KEY_MINUTE  = intPreferencesKey("reminder_minute")
        private val KEY_LEAD    = intPreferencesKey("reminder_lead_min")
    }
}
