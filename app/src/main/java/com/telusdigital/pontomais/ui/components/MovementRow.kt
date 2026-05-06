package com.telusdigital.pontomais.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDownward
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.telusdigital.pontomais.ui.theme.PontoMaisTheme
import com.telusdigital.pontomais.ui.theme.Slate
import com.telusdigital.pontomais.ui.theme.TelusPurple

enum class MovementType { Credit, Debit }

data class Movement(
    val type: MovementType,
    val description: String,
    val date: String,
    val value: String,
)

@Composable
fun MovementRow(
    movement: Movement,
    showDivider: Boolean = true,
) {
    val isCredit  = movement.type == MovementType.Credit
    val iconColor = if (isCredit) TelusPurple else Amber
    val iconBg    = if (isCredit) Iris else AmberContainer
    val valueColor = iconColor

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Surface(
                shape = CircleShape,
                color = iconBg,
                modifier = Modifier.size(36.dp),
            ) {
                Icon(
                    imageVector = if (isCredit) Icons.Outlined.ArrowUpward else Icons.Outlined.ArrowDownward,
                    contentDescription = if (isCredit) "Crédito" else "Débito",
                    tint = iconColor,
                    modifier = Modifier
                        .padding(9.dp)
                        .size(18.dp),
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text  = movement.description,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                        color = Obsidian,
                    ),
                )
                Text(
                    text  = movement.date,
                    style = MaterialTheme.typography.bodySmall.copy(color = Slate),
                )
            }

            Text(
                text  = movement.value,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    color = valueColor,
                ),
            )
        }
        if (showDivider) HorizontalDivider(color = Marble, thickness = 1.dp)
    }
}

@Preview(showBackground = true)
@Composable
private fun MovementRowPreview() {
    PontoMaisTheme {
        Surface(color = Color.White) {
            Column {
                MovementRow(Movement(MovementType.Credit, "Hora extra · ter", "5 mai 2026", "+00:32"))
                MovementRow(Movement(MovementType.Debit, "Saída antecipada", "6 mai 2026", "−00:12"))
                MovementRow(Movement(MovementType.Credit, "Plantão sábado", "28 abr 2026", "+04:00"), showDivider = false)
            }
        }
    }
}
