package com.telusdigital.pontomais.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.telusdigital.pontomais.ui.theme.Amber
import com.telusdigital.pontomais.ui.theme.AmberContainer
import com.telusdigital.pontomais.ui.theme.Iris
import com.telusdigital.pontomais.ui.theme.Marble
import com.telusdigital.pontomais.ui.theme.Obsidian
import com.telusdigital.pontomais.ui.theme.Orchid
import com.telusdigital.pontomais.ui.theme.PontoMaisTheme
import com.telusdigital.pontomais.ui.theme.Slate
import com.telusdigital.pontomais.ui.theme.TelusPurple

enum class DayStatus(val label: String, val color: Color, val bgColor: Color) {
    Complete("OK", TelusPurple, Iris),
    Over("Extra", TelusPurple, Iris),
    Short("Atraso", Amber, AmberContainer),
    Holiday("Feriado", TelusPurple, Iris),
}

data class DayEntry(
    val date: String,
    val total: String,
    val overtime: String,
    val status: DayStatus,
    val punches: List<String> = emptyList(),
)

private val punchLabels = listOf("Entrada", "Pausa", "Volta", "Saída")

@Composable
fun DayRow(
    day: DayEntry,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Marble),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = modifier.fillMaxWidth(),
    ) {
        // Header row
        Surface(
            onClick = { expanded = !expanded },
            color = Color.Transparent,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text  = day.date,
                        style = MaterialTheme.typography.titleSmall.copy(color = Obsidian),
                    )
                    Text(
                        text  = if (day.punches.isNotEmpty()) "${day.punches.size} batidas · ${day.overtime}" else day.overtime,
                        style = MaterialTheme.typography.bodySmall.copy(color = Slate),
                    )
                }

                // Status badge
                Surface(
                    shape = CircleShape,
                    color = day.status.bgColor,
                ) {
                    Text(
                        text  = day.status.label,
                        style = MaterialTheme.typography.labelSmall.copy(color = day.status.color),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                    )
                }

                Text(
                    text  = day.total,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Obsidian,
                        letterSpacing = (-0.02f).let {
                            androidx.compose.ui.unit.TextUnit(it, androidx.compose.ui.unit.TextUnitType.Em)
                        },
                    ),
                    modifier = Modifier.width(64.dp),
                )

                Icon(
                    imageVector = if (expanded) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore,
                    contentDescription = if (expanded) "Recolher" else "Expandir",
                    tint = Slate,
                    modifier = Modifier.size(18.dp),
                )
            }
        }

        // Expanded punch timeline
        AnimatedVisibility(visible = expanded && day.punches.isNotEmpty()) {
            Column {
                HorizontalDivider(color = Marble)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    day.punches.forEachIndexed { i, time ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f),
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                // Connector line (between dots)
                                if (i < day.punches.size - 1) {
                                    Box(
                                        modifier = Modifier
                                            .offset(x = 6.dp)
                                            .fillMaxWidth()
                                            .height(2.dp)
                                            .padding(start = 6.dp),
                                    )
                                }
                                Surface(
                                    shape = CircleShape,
                                    color = if (i % 2 == 0) TelusPurple else Orchid,
                                    modifier = Modifier.size(12.dp),
                                ) {}
                            }
                            Text(
                                text  = time,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                                    color = Obsidian,
                                ),
                                modifier = Modifier.padding(top = 6.dp),
                            )
                            Text(
                                text  = punchLabels.getOrElse(i) { "" },
                                style = MaterialTheme.typography.labelSmall.copy(color = Slate),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF2F3ED)
@Composable
private fun DayRowPreview() {
    PontoMaisTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            DayRow(DayEntry("Sex, 8 mai", "08:12", "+12 min", DayStatus.Complete, listOf("09:02", "12:30", "13:31", "18:14")))
            DayRow(DayEntry("Qua, 6 mai", "07:48", "−12 min", DayStatus.Short, listOf("09:10", "12:30", "13:35", "17:48")))
            DayRow(DayEntry("Sex, 1 mai", "—", "Feriado", DayStatus.Holiday))
        }
    }
}
