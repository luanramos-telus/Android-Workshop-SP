package com.telusdigital.pontomais.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.TrendingUp
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.telusdigital.pontomais.ui.components.CaradonnaBrush
import com.telusdigital.pontomais.ui.components.HeroStatusCard
import com.telusdigital.pontomais.ui.components.NotificationIconButton
import com.telusdigital.pontomais.ui.components.PontoBottomNavBar
import com.telusdigital.pontomais.ui.components.PontoTab
import com.telusdigital.pontomais.ui.components.PontoTopAppBar
import com.telusdigital.pontomais.ui.components.PunchRow
import com.telusdigital.pontomais.ui.components.PunchType
import com.telusdigital.pontomais.ui.components.StatCard
import com.telusdigital.pontomais.ui.theme.Marble
import com.telusdigital.pontomais.ui.theme.Obsidian
import com.telusdigital.pontomais.ui.theme.Pearl
import com.telusdigital.pontomais.ui.theme.PontoMaisTheme
import com.telusdigital.pontomais.ui.theme.Slate
import com.telusdigital.pontomais.ui.theme.TelusPurple
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

private val clockFmt = DateTimeFormatter.ofPattern("HH:mm")

@Composable
fun HomeScreen(
    onNavigate: (PontoTab) -> Unit,
    currentTab: PontoTab = PontoTab.Home,
    vm: HomeViewModel = viewModel(),
) {
    val state by vm.uiState.collectAsState()

    var currentTime by remember { mutableStateOf(LocalTime.now().format(clockFmt)) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(60_000)
            currentTime = LocalTime.now().format(clockFmt)
        }
    }

    val ptBr     = Locale.forLanguageTag("pt-BR")
    val today    = LocalDate.now()
    val dayName  = today.dayOfWeek.getDisplayName(TextStyle.FULL, ptBr)
    val dateLong = "$dayName, ${today.dayOfMonth} de ${today.month.getDisplayName(TextStyle.FULL, ptBr)}"

    val isWorking = state.punches.isNotEmpty() && state.punches.last().type != PunchType.Out

    // Only show Entrada (first In) and Saída (last Out) in the summary list.
    val visiblePunches = buildList {
        state.punches.firstOrNull { it.type == PunchType.In }?.let { add(it) }
        state.punches.lastOrNull { it.type == PunchType.Out }?.let { add(it) }
    }

    Scaffold(
        topBar = {
            PontoTopAppBar(
                title          = "Olá, Luan",
                leadingContent = { AvatarInitials("LR") },
                actions        = { NotificationIconButton(badgeCount = 2) },
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
            // Hero card
            HeroStatusCard(
                time      = currentTime,
                date      = dateLong,
                isWorking = isWorking,
                onPunch   = vm::punch,
                modifier  = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            )

            // Stat cards
            Row(
                modifier              = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                StatCard(
                    overline = "HOJE",
                    value    = state.workedToday,
                    sub      = if (isWorking) "em curso" else "trabalhadas",
                    modifier = Modifier.weight(1f),
                )
                StatCard(
                    overline  = "BANCO DE HORAS",
                    value     = state.hoursBalance,
                    sub       = "saldo positivo",
                    trendIcon = Icons.AutoMirrored.Outlined.TrendingUp,
                    modifier  = Modifier.weight(1f),
                    onClick   = { onNavigate(PontoTab.Bank) },
                )
            }

            Spacer(Modifier.height(8.dp))

            // Batidas de hoje — Entrada + Saída only
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    verticalAlignment     = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text  = "Batidas de hoje",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color      = Obsidian,
                        ),
                    )
                    TextButton(
                        onClick        = { onNavigate(PontoTab.History) },
                        contentPadding = PaddingValues(horizontal = 4.dp),
                    ) {
                        Text(
                            text  = "Ver histórico",
                            style = MaterialTheme.typography.bodySmall.copy(color = TelusPurple),
                        )
                        Icon(
                            imageVector = Icons.Outlined.ChevronRight,
                            contentDescription = null,
                            tint     = TelusPurple,
                            modifier = Modifier.size(14.dp),
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                Card(
                    shape     = RoundedCornerShape(16.dp),
                    colors    = CardDefaults.cardColors(containerColor = Color.White),
                    border    = BorderStroke(1.dp, Marble),
                    elevation = CardDefaults.cardElevation(0.dp),
                    modifier  = Modifier.fillMaxWidth(),
                ) {
                    if (visiblePunches.isEmpty()) {
                        Text(
                            text     = "Nenhuma batida registrada ainda.",
                            style    = MaterialTheme.typography.bodySmall.copy(color = Slate),
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp),
                        )
                    } else {
                        visiblePunches.forEachIndexed { i, punch ->
                            PunchRow(punch = punch, showDivider = i < visiblePunches.size - 1)
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun AvatarInitials(initials: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(start = 12.dp)
            .size(40.dp)
            .clip(CircleShape)
            .background(CaradonnaBrush),
    ) {
        Text(
            text  = initials,
            style = MaterialTheme.typography.labelMedium.copy(
                color      = Color.White,
                fontWeight = FontWeight.Bold,
            ),
        )
    }
}

@Preview(showBackground = true, device = "spec:width=412dp,height=892dp")
@Composable
private fun HomeScreenIdlePreview() {
    PontoMaisTheme {
        HomeScreen(onNavigate = {})
    }
}
