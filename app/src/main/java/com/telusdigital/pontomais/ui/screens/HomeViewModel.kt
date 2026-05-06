package com.telusdigital.pontomais.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

data class HomeUiState(
    val punches: List<PunchEntry> = listOf(
        PunchEntry(PunchType.In,    "09:02", "Escritório · POA", synced = true),
        PunchEntry(PunchType.Pause, "12:30", "Escritório · POA", synced = true),
        PunchEntry(PunchType.Back,  "13:31", "Escritório · POA", synced = true),
    ),
    val workedToday: String = "06:42",
    val hoursBalance: String = "+12:38",
)

private val punchOrder = listOf(PunchType.In, PunchType.Pause, PunchType.Back, PunchType.Out)
private val timeFmt    = DateTimeFormatter.ofPattern("HH:mm")

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun punch() {
        val current = _uiState.value.punches
        val nextType = if (current.size >= punchOrder.size) {
            PunchType.In
        } else {
            punchOrder[current.size]
        }
        val time = LocalTime.now().format(timeFmt)
        val newEntry = PunchEntry(nextType, time, "Escritório · POA", synced = false)
        val newList  = if (current.size >= punchOrder.size) listOf(newEntry) else current + newEntry

        _uiState.update { it.copy(punches = newList) }

        // Simulate sync after 1.2 s
        viewModelScope.launch {
            delay(1200)
            _uiState.update { state ->
                state.copy(punches = state.punches.map { it.copy(synced = true) })
            }
        }
    }
}
