package com.telusdigital.pontomais.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.telusdigital.pontomais.ui.components.DayEntry
import com.telusdigital.pontomais.ui.components.DayRow
import com.telusdigital.pontomais.ui.components.DayStatus
import com.telusdigital.pontomais.ui.components.PontoBottomNavBar
import com.telusdigital.pontomais.ui.components.PontoFilterChip
import com.telusdigital.pontomais.ui.components.PontoTab
import com.telusdigital.pontomais.ui.components.PontoTopAppBar
import com.telusdigital.pontomais.ui.components.WeekSummary
import com.telusdigital.pontomais.ui.components.WeekSummaryCard
import com.telusdigital.pontomais.ui.theme.Obsidian
import com.telusdigital.pontomais.ui.theme.Pearl
import com.telusdigital.pontomais.ui.theme.PontoMaisTheme

private val sampleDays = listOf(
    DayEntry("Sex, 8 mai",  "08:12", "+12 min",  DayStatus.Complete, listOf("09:02", "12:30", "13:31", "18:14")),
    DayEntry("Qui, 7 mai",  "08:04", "+04 min",  DayStatus.Complete, listOf("08:58", "12:25", "13:28", "18:02")),
    DayEntry("Qua, 6 mai",  "07:48", "−12 min",  DayStatus.Short,    listOf("09:10", "12:30", "13:35", "17:48")),
    DayEntry("Ter, 5 mai",  "08:32", "+32 min",  DayStatus.Over,     listOf("08:45", "12:30", "13:31", "18:32")),
    DayEntry("Seg, 4 mai",  "08:00", "00 min",   DayStatus.Complete, listOf("09:00", "12:30", "13:30", "18:00")),
    DayEntry("Sex, 1 mai",  "—",     "Feriado",  DayStatus.Holiday),
)

private val sampleSummary = WeekSummary(
    periodLabel      = "SEMANA DE 4 A 10 MAI",
    totalHours       = "40:36",
    expectedHours    = "40:00",
    progressFraction = 1.01f,
    extras           = "+36 min",
    absences         = "00 min",
    delays           = "12 min",
)

@Composable
fun HistoryScreen(
    onNavigate: (PontoTab) -> Unit,
    onBack: () -> Unit = {},
    currentTab: PontoTab = PontoTab.History,
) {
    var filter by remember { mutableStateOf("week") }
    val chipFilters = listOf("week" to "Esta semana", "month" to "Este mês", "custom" to "Personalizado")

    Scaffold(
        topBar = {
            PontoTopAppBar(
                title = "Histórico",
                navigationIcon = Icons.AutoMirrored.Outlined.ArrowBack,
                onNavigationClick = onBack,
                actions = {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Outlined.FileDownload, contentDescription = "Baixar")
                    }
                },
            )
        },
        bottomBar = {
            PontoBottomNavBar(currentTab = currentTab, onTabSelected = onNavigate)
        },
        containerColor = Pearl,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
        ) {
            // Filter chips
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                chipFilters.forEach { (id, label) ->
                    PontoFilterChip(
                        label    = label,
                        selected = filter == id,
                        onClick  = { filter = id },
                    )
                }
            }

            // Summary card
            WeekSummaryCard(
                summary  = sampleSummary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            )

            Spacer(Modifier.height(12.dp))

            // Day list
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                sampleDays.forEach { day ->
                    DayRow(day = day)
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true, device = "spec:width=412dp,height=892dp")
@Composable
private fun HistoryScreenPreview() {
    PontoMaisTheme {
        HistoryScreen(onNavigate = {})
    }
}
