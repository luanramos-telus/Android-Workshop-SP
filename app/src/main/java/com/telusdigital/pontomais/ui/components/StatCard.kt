package com.telusdigital.pontomais.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.telusdigital.pontomais.ui.theme.Iris
import com.telusdigital.pontomais.ui.theme.Marble
import com.telusdigital.pontomais.ui.theme.Obsidian
import com.telusdigital.pontomais.ui.theme.PontoMaisTheme
import com.telusdigital.pontomais.ui.theme.Slate
import com.telusdigital.pontomais.ui.theme.TelusPurple

@Composable
fun StatCard(
    overline: String,
    value: String,
    sub: String,
    modifier: Modifier = Modifier,
    trendIcon: ImageVector? = null,
    accentColor: Color = TelusPurple,
    onClick: (() -> Unit)? = null,
) {
    Card(
        onClick = onClick ?: {},
        enabled = onClick != null,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Marble),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text  = overline,
                    style = MaterialTheme.typography.labelSmall.copy(
                        letterSpacing = androidx.compose.ui.unit.TextUnit(0.08f, androidx.compose.ui.unit.TextUnitType.Em),
                        color = accentColor,
                    ),
                )
                if (trendIcon != null) {
                    Surface(
                        shape = CircleShape,
                        color = Iris,
                        modifier = Modifier.size(22.dp),
                    ) {
                        Icon(
                            imageVector = trendIcon,
                            contentDescription = null,
                            tint = accentColor,
                            modifier = Modifier
                                .padding(4.dp)
                                .size(14.dp),
                        )
                    }
                }
            }
            Text(
                text  = value,
                style = MaterialTheme.typography.headlineSmall.copy(
                    letterSpacing = (-0.02f).let {
                        androidx.compose.ui.unit.TextUnit(it, androidx.compose.ui.unit.TextUnitType.Em)
                    },
                    color = Obsidian,
                ),
            )
            Text(
                text  = sub,
                style = MaterialTheme.typography.bodySmall.copy(color = Slate),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF2F3ED)
@Composable
private fun StatCardPreview() {
    PontoMaisTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            StatCard(
                overline = "HOJE",
                value    = "06:42",
                sub      = "em curso",
                modifier = Modifier.weight(1f),
            )
            StatCard(
                overline  = "BANCO DE HORAS",
                value     = "+12:38",
                sub       = "saldo positivo",
                trendIcon = Icons.AutoMirrored.Outlined.TrendingUp,
                modifier  = Modifier.weight(1f),
                onClick   = {},
            )
        }
    }
}
