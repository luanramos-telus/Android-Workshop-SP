package com.telusdigital.pontomais.ui.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.telusdigital.pontomais.reminder.ReminderScheduler
import com.telusdigital.pontomais.ui.components.PunchEntry
import com.telusdigital.pontomais.ui.components.PunchType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

private val displayFmt = DateTimeFormatter.ofPattern("HH:mm")

// Internal record — holds actual LocalTime for duration math.
private data class PunchRecord(
    val type: PunchType,
    val time: LocalTime,
    val location: String = "Escritório · POA",
    val synced: Boolean = true,
)

data class HomeUiState(
    val punches: List<PunchEntry> = emptyList(),
    val workedToday: String = "00:00",
    val hoursBalance: String = "+12:38",
)

class HomeViewModel(app: Application) : AndroidViewModel(app) {

    private val records = mutableListOf<PunchRecord>()

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        // Refresh worked time every 30 s while the user is clocked in.
        viewModelScope.launch {
            while (true) {
                delay(30_000)
                if (isWorking()) refreshState()
            }
        }
    }

    fun punch() {
        // Simple toggle: last was Out (or empty) → Entrada; last was In → Saída.
        // No limit and no clearing — list grows indefinitely.
        val nextType = if (records.lastOrNull()?.type == PunchType.In) PunchType.Out else PunchType.In

        records += PunchRecord(type = nextType, time = LocalTime.now(), synced = false)
        refreshState()

        // Cancel today's pending reminder — the user has already punched. (AC 5)
        viewModelScope.launch { ReminderScheduler.cancelToday(getApplication()) }

        viewModelScope.launch {
            delay(1_200)
            val idx = records.indices.last
            records[idx] = records[idx].copy(synced = true)
            refreshState()
        }
    }

    private fun isWorking(): Boolean = records.lastOrNull()?.type == PunchType.In

    private fun refreshState() {
        _uiState.update {
            it.copy(
                punches     = records.map { r -> r.toEntry() },
                workedToday = workedMinutes().formatDuration(),
            )
        }
    }

    private fun workedMinutes(): Long {
        var total = 0L
        var segStart: LocalTime? = null

        for (r in records) {
            when (r.type) {
                PunchType.In  -> segStart = r.time
                PunchType.Out -> {
                    segStart?.let { total += ChronoUnit.MINUTES.between(it, r.time) }
                    segStart = null
                }
                else -> Unit
            }
        }
        // Open segment — currently working.
        segStart?.let { total += ChronoUnit.MINUTES.between(it, LocalTime.now()) }
        return total
    }

    private fun Long.formatDuration(): String {
        val h = this / 60
        val m = this % 60
        return "%02d:%02d".format(h, m)
    }

    private fun PunchRecord.toEntry() = PunchEntry(
        type     = type,
        time     = time.format(displayFmt),
        location = location,
        synced   = synced,
    )
}
