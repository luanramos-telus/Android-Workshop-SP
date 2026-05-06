package com.telusdigital.pontomais.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.telusdigital.pontomais.ui.theme.Iris
import com.telusdigital.pontomais.ui.theme.Moonstone
import com.telusdigital.pontomais.ui.theme.Obsidian
import com.telusdigital.pontomais.ui.theme.PontoMaisTheme
import com.telusdigital.pontomais.ui.theme.Slate
import com.telusdigital.pontomais.ui.theme.TelusPurple

@Composable
fun ScheduleCard(
    schedule: String,
    detail: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = Moonstone,
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Iris,
                modifier = Modifier.size(48.dp),
            ) {
                Icon(
                    imageVector = Icons.Outlined.CalendarMonth,
                    contentDescription = null,
                    tint = TelusPurple,
                    modifier = Modifier
                        .padding(13.dp)
                        .size(22.dp),
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text  = "Sua jornada",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = TelusPurple,
                        letterSpacing = androidx.compose.ui.unit.TextUnit(0.08f, androidx.compose.ui.unit.TextUnitType.Em),
                    ),
                )
                Text(
                    text  = schedule,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Obsidian),
                )
                Text(
                    text  = detail,
                    style = MaterialTheme.typography.bodySmall.copy(color = Slate),
                )
            }

            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = Slate,
                modifier = Modifier.size(20.dp),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF2F3ED)
@Composable
private fun ScheduleCardPreview() {
    PontoMaisTheme {
        ScheduleCard(
            schedule  = "Segunda a sexta · 09:00 às 18:00",
            detail    = "1h de almoço · Híbrido (3 dias presencial)",
            modifier  = Modifier.padding(16.dp),
        )
    }
}
