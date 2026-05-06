package com.telusdigital.pontomais.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.TrendingUp
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.telusdigital.pontomais.ui.components.GradientCard
import com.telusdigital.pontomais.ui.components.Movement
import com.telusdigital.pontomais.ui.components.MovementRow
import com.telusdigital.pontomais.ui.components.MovementType
import com.telusdigital.pontomais.ui.components.PontoBottomNavBar
import com.telusdigital.pontomais.ui.components.PontoButton
import com.telusdigital.pontomais.ui.components.PontoOutlinedButton
import com.telusdigital.pontomais.ui.components.PontoTab
import com.telusdigital.pontomais.ui.components.PontoTopAppBar
import com.telusdigital.pontomais.ui.theme.Juniper
import com.telusdigital.pontomais.ui.theme.Marble
import com.telusdigital.pontomais.ui.theme.Obsidian
import com.telusdigital.pontomais.ui.theme.Pearl
import com.telusdigital.pontomais.ui.theme.PontoMaisTheme
import com.telusdigital.pontomais.ui.theme.Slate
import com.telusdigital.pontomais.ui.theme.TelusPurple

private val sampleMovements = listOf(
    Movement(MovementType.Credit, "Hora extra · ter",  "5 mai 2026",  "+00:32"),
    Movement(MovementType.Credit, "Hora extra · sex",  "8 mai 2026",  "+00:12"),
    Movement(MovementType.Debit,  "Saída antecipada",  "6 mai 2026",  "−00:12"),
    Movement(MovementType.Credit, "Plantão sábado",    "28 abr 2026", "+04:00"),
    Movement(MovementType.Debit,  "Folga compensada",  "24 abr 2026", "−08:00"),
    Movement(MovementType.Credit, "Hora extra · qui",  "18 abr 2026", "+01:24"),
)

@Composable
fun BankScreen(
    onNavigate: (PontoTab) -> Unit,
    onBack: () -> Unit = {},
    currentTab: PontoTab = PontoTab.Bank,
) {
    Scaffold(
        topBar = {
            PontoTopAppBar(
                title             = "Banco de horas",
                navigationIcon    = Icons.AutoMirrored.Outlined.ArrowBack,
                onNavigationClick = onBack,
                actions = {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.AutoMirrored.Outlined.HelpOutline, contentDescription = "Ajuda")
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
            // ── Hero balance ──────────────────────────────────────────────────
            GradientCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text  = "SALDO ATUAL",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Juniper,
                            letterSpacing = androidx.compose.ui.unit.TextUnit(0.08f, androidx.compose.ui.unit.TextUnitType.Em),
                        ),
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text  = "+12:38",
                            style = MaterialTheme.typography.displayMedium.copy(
                                color = Color.White,
                                letterSpacing = (-0.03f).let {
                                    androidx.compose.ui.unit.TextUnit(it, androidx.compose.ui.unit.TextUnitType.Em)
                                },
                            ),
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text  = "horas",
                            style = MaterialTheme.typography.titleMedium.copy(color = Color.White.copy(alpha = 0.7f)),
                            modifier = Modifier.padding(bottom = 8.dp),
                        )
                    }
                    Text(
                        text  = "Atualizado em 8 mai · 18:14",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.85f)),
                    )
                    Spacer(Modifier.height(20.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        PontoButton(
                            label          = "Solicitar folga",
                            onClick        = {},
                            icon           = Icons.Outlined.CalendarMonth,
                            containerColor = Color.White,
                            contentColor   = TelusPurple,
                            modifier       = Modifier.weight(1f),
                        )
                        PontoOutlinedButton(
                            label        = "Ver projeção",
                            onClick      = {},
                            icon         = Icons.AutoMirrored.Outlined.TrendingUp,
                            borderColor  = Color.White.copy(alpha = 0.3f),
                            contentColor = Color.White,
                            modifier     = Modifier.weight(1f),
                        )
                    }
                }
            }

            // ── Cycle progress ────────────────────────────────────────────────
            Card(
                shape     = RoundedCornerShape(16.dp),
                colors    = CardDefaults.cardColors(containerColor = Color.White),
                border    = BorderStroke(1.dp, Marble),
                elevation = CardDefaults.cardElevation(0.dp),
                modifier  = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment     = Alignment.Top,
                    ) {
                        Column {
                            Text(
                                text  = "CICLO ATUAL",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = TelusPurple,
                                    letterSpacing = androidx.compose.ui.unit.TextUnit(0.08f, androidx.compose.ui.unit.TextUnitType.Em),
                                ),
                            )
                            Text(
                                text  = "1 mai 2026 a 31 jul 2026",
                                style = MaterialTheme.typography.bodySmall.copy(color = Obsidian),
                                modifier = Modifier.padding(top = 4.dp),
                            )
                        }
                        Text(
                            text  = "83 dias restantes",
                            style = MaterialTheme.typography.bodySmall.copy(color = Slate),
                        )
                    }

                    Spacer(Modifier.height(14.dp))

                    CycleProgressBar(progress = 0.15f)

                    Spacer(Modifier.height(8.dp))

                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text("0h",       style = MaterialTheme.typography.labelSmall.copy(color = Slate))
                        Text("Limite 40h", style = MaterialTheme.typography.labelSmall.copy(color = Obsidian, fontWeight = FontWeight.SemiBold))
                        Text("80h",      style = MaterialTheme.typography.labelSmall.copy(color = Slate))
                    }
                }
            }

            // ── Movements ─────────────────────────────────────────────────────
            Row(
                modifier              = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically,
            ) {
                Text(
                    text  = "Movimentações",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = Obsidian),
                )
                TextButton(onClick = {}) {
                    Text("Ver todas", style = MaterialTheme.typography.bodySmall.copy(color = TelusPurple))
                }
            }

            Card(
                shape     = RoundedCornerShape(16.dp),
                colors    = CardDefaults.cardColors(containerColor = Color.White),
                border    = BorderStroke(1.dp, Marble),
                elevation = CardDefaults.cardElevation(0.dp),
                modifier  = Modifier.padding(horizontal = 16.dp),
            ) {
                sampleMovements.forEachIndexed { i, movement ->
                    MovementRow(movement = movement, showDivider = i < sampleMovements.size - 1)
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun CycleProgressBar(progress: Float) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(Marble),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress.coerceIn(0f, 1f))
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(TelusPurple),
        )
    }
}

@Preview(showBackground = true, device = "spec:width=412dp,height=892dp")
@Composable
private fun BankScreenPreview() {
    PontoMaisTheme {
        BankScreen(onNavigate = {})
    }
}
