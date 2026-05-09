package com.telusdigital.pontomais.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.telusdigital.pontomais.ui.theme.AmberContainer
import com.telusdigital.pontomais.ui.theme.Juniper
import com.telusdigital.pontomais.ui.theme.PontoMaisTheme

data class WeekSummary(
    val periodLabel: String,
    val totalHours: String,
    val expectedHours: String,
    val progressFraction: Float,
    val extras: String,
    val absences: String,
    val delays: String,
)

@Composable
fun WeekSummaryCard(
    summary: WeekSummary,
    modifier: Modifier = Modifier,
) {
    GradientCard(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text  = summary.periodLabel,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Juniper,
                    letterSpacing = androidx.compose.ui.unit.TextUnit(0.08f, androidx.compose.ui.unit.TextUnitType.Em),
                ),
            )

            Spacer(Modifier.height(8.dp))

            Row(
                verticalAlignment = androidx.compose.ui.Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    text  = summary.totalHours,
                    style = MaterialTheme.typography.displaySmall.copy(
                        color = Color.White,
                        letterSpacing = (-0.03f).let {
                            androidx.compose.ui.unit.TextUnit(it, androidx.compose.ui.unit.TextUnitType.Em)
                        },
                    ),
                )
                Text(
                    text  = "de ${summary.expectedHours} previstas",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.White.copy(alpha = 0.78f),
                    ),
                    modifier = Modifier.padding(bottom = 6.dp),
                )
            }

            Spacer(Modifier.height(14.dp))

            // Progress bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(Color.White.copy(alpha = 0.18f)),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(summary.progressFraction.coerceIn(0f, 1f))
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(Juniper),
                )
            }

            Spacer(Modifier.height(14.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                MiniStat(label = "Extras",  value = summary.extras,    valueColor = Juniper)
                MiniStat(label = "Faltas",  value = summary.absences,  valueColor = Color.White.copy(alpha = 0.85f))
                MiniStat(label = "Atrasos", value = summary.delays,    valueColor = AmberContainer)
            }
        }
    }
}

@Composable
private fun MiniStat(label: String, value: String, valueColor: Color) {
    Column {
        Text(
            text  = label,
            style = MaterialTheme.typography.labelSmall.copy(
                color = Color.White.copy(alpha = 0.7f),
            ),
        )
        Text(
            text  = value,
            style = MaterialTheme.typography.labelLarge.copy(color = valueColor),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF2F3ED)
@Composable
private fun WeekSummaryCardPreview() {
    PontoMaisTheme {
        WeekSummaryCard(
            summary = WeekSummary(
                periodLabel      = "SEMANA DE 4 A 10 MAI",
                totalHours       = "40:36",
                expectedHours    = "40:00",
                progressFraction = 1.01f,
                extras           = "+36 min",
                absences         = "00 min",
                delays           = "12 min",
            ),
            modifier = Modifier.padding(16.dp),
        )
    }
}
