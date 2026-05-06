package com.telusdigital.pontomais.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDownward
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Coffee
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Wifi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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

enum class PunchType(
    val label: String,
    val icon: ImageVector,
    val iconColor: Color,
    val iconBg: Color,
) {
    In("Entrada", Icons.Outlined.ArrowDownward, TelusPurple, Iris),
    Pause("Início pausa", Icons.Outlined.Coffee, Amber, AmberContainer),
    Back("Volta pausa", Icons.Outlined.ArrowUpward, TelusPurple, Iris),
    Out("Saída", Icons.Outlined.ArrowUpward, TelusPurple, Iris),
}

data class PunchEntry(
    val type: PunchType,
    val time: String,
    val location: String,
    val synced: Boolean,
)

@Composable
fun PunchRow(
    punch: PunchEntry,
    showDivider: Boolean = true,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = punch.type.iconBg,
                modifier = Modifier.size(40.dp),
            ) {
                Icon(
                    imageVector = punch.type.icon,
                    contentDescription = punch.type.label,
                    tint = punch.type.iconColor,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(20.dp),
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text  = punch.type.label,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                        color = Obsidian,
                    ),
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = Slate,
                        modifier = Modifier.size(11.dp),
                    )
                    Text(
                        text  = punch.location,
                        style = MaterialTheme.typography.bodySmall.copy(color = Slate),
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text  = punch.time,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Obsidian,
                        letterSpacing = (-0.02f).let {
                            androidx.compose.ui.unit.TextUnit(it, androidx.compose.ui.unit.TextUnitType.Em)
                        },
                    ),
                )
                val syncColor = if (punch.synced) TelusPurple else Amber
                val syncIcon  = if (punch.synced) Icons.Outlined.Check else Icons.Outlined.Wifi
                val syncText  = if (punch.synced) "sincronizado" else "sincronizando"
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                ) {
                    Icon(imageVector = syncIcon, contentDescription = null, tint = syncColor, modifier = Modifier.size(11.dp))
                    Text(text = syncText, style = MaterialTheme.typography.labelSmall.copy(color = syncColor))
                }
            }
        }
        if (showDivider) HorizontalDivider(color = Marble, thickness = 1.dp)
    }
}

@Preview(showBackground = true)
@Composable
private fun PunchRowPreview() {
    PontoMaisTheme {
        Surface(color = Color.White) {
            Column {
                PunchRow(PunchEntry(PunchType.In, "09:02", "Escritório · POA", true))
                PunchRow(PunchEntry(PunchType.Pause, "12:30", "Escritório · POA", true))
                PunchRow(PunchEntry(PunchType.Back, "13:31", "Escritório · POA", false), showDivider = false)
            }
        }
    }
}
