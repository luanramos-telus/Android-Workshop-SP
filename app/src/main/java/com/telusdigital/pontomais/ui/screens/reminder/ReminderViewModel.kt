package com.telusdigital.pontomais.ui.screens.reminder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.telusdigital.pontomais.data.ReminderPreferences
import com.telusdigital.pontomais.reminder.ReminderConfig
import com.telusdigital.pontomais.reminder.ReminderScheduler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class ReminderUiState(
    val loaded: Boolean = false,
    val draft: ReminderConfig = ReminderConfig(),
    val saved: ReminderConfig = ReminderConfig(),
) {
    val isDirty: Boolean get() = loaded && draft != saved
}

class ReminderViewModel(app: Application) : AndroidViewModel(app) {

    private val prefs = ReminderPreferences(app.applicationContext)

    private val _state = MutableStateFlow(ReminderUiState())
    val state: StateFlow<ReminderUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val current = prefs.config.first()
            _state.value = ReminderUiState(loaded = true, draft = current, saved = current)
        }
    }

    fun setEnabled(enabled: Boolean) = update { it.copy(enabled = enabled) }
    fun setHour(hour: Int)          = update { it.copy(hour = ((hour % 24) + 24) % 24) }
    fun setMinute(minute: Int)      = update { it.copy(minute = ((minute % 60) + 60) % 60) }
    fun setLeadMin(leadMin: Int)    = update { it.copy(leadMin = leadMin) }

    fun save(onDone: () -> Unit) {
        val draft = _state.value.draft
        viewModelScope.launch {
            prefs.save(draft)
            ReminderScheduler.reschedule(getApplication(), draft)
            _state.value = _state.value.copy(saved = draft)
            onDone()
        }
    }

    private inline fun update(transform: (ReminderConfig) -> ReminderConfig) {
        _state.value = _state.value.copy(draft = transform(_state.value.draft))
    }
}
