package com.telusdigital.pontomais.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.telusdigital.pontomais.ui.theme.Iris
import com.telusdigital.pontomais.ui.theme.Marble
import com.telusdigital.pontomais.ui.theme.Obsidian
import com.telusdigital.pontomais.ui.theme.PontoMaisTheme
import com.telusdigital.pontomais.ui.theme.TelusPurple

@Composable
fun PontoFilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FilterChip(
        selected = selected,
        onClick  = onClick,
        label    = {
            Text(
                text  = label,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = if (selected) androidx.compose.ui.text.font.FontWeight.SemiBold
                                 else androidx.compose.ui.text.font.FontWeight.Medium,
                ),
            )
        },
        leadingIcon = if (selected) {
            { Icon(imageVector = Icons.Outlined.Check, contentDescription = null) }
        } else null,
        modifier = modifier,
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor         = Iris,
            selectedLabelColor             = TelusPurple,
            selectedLeadingIconColor       = TelusPurple,
            containerColor                 = androidx.compose.ui.graphics.Color.White,
            labelColor                     = Obsidian,
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled                 = true,
            selected                = selected,
            selectedBorderColor     = TelusPurple,
            selectedBorderWidth     = 1.dp,
            borderColor             = Marble,
        ),
    )
}

@Preview(showBackground = true)
@Composable
private fun PontoFilterChipPreview() {
    PontoMaisTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            PontoFilterChip(label = "Esta semana", selected = true, onClick = {})
            PontoFilterChip(label = "Este mês", selected = false, onClick = {})
            PontoFilterChip(label = "Personalizado", selected = false, onClick = {})
        }
    }
}
