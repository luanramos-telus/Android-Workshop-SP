package com.telusdigital.pontomais.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun ProfileMenuRow(
    icon: ImageVector,
    label: String,
    modifier: Modifier = Modifier,
    sub: String? = null,
    showDivider: Boolean = true,
    trailing: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {},
) {
    Column {
        Surface(
            onClick = onClick,
            color = Color.Transparent,
            modifier = modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Iris,
                    modifier = Modifier.size(36.dp),
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = TelusPurple,
                        modifier = Modifier
                            .padding(9.dp)
                            .size(18.dp),
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text  = label,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                            color = Obsidian,
                        ),
                    )
                    if (sub != null) {
                        Text(
                            text  = sub,
                            style = MaterialTheme.typography.bodySmall.copy(color = Slate),
                        )
                    }
                }

                trailing?.invoke() ?: Icon(
                    imageVector = Icons.Outlined.ChevronRight,
                    contentDescription = null,
                    tint = Slate,
                    modifier = Modifier.size(18.dp),
                )
            }
        }
        if (showDivider) HorizontalDivider(color = Marble, thickness = 1.dp)
    }
}

@Composable
fun PontoToggle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
            checkedTrackColor   = TelusPurple,
            checkedThumbColor   = Color.White,
            uncheckedTrackColor = Marble,
            uncheckedThumbColor = Color.White,
        ),
    )
}

@Preview(showBackground = true)
@Composable
private fun ProfileMenuRowPreview() {
    var notifications by remember { mutableStateOf(true) }
    var darkMode by remember { mutableStateOf(false) }

    PontoMaisTheme {
        Surface(color = Color.White) {
            Column {
                ProfileMenuRow(icon = Icons.Outlined.Edit, label = "Solicitar ajuste", sub = "Corrigir uma batida")
                ProfileMenuRow(
                    icon = Icons.Outlined.Notifications,
                    label = "Notificações",
                    trailing = { PontoToggle(checked = notifications, onCheckedChange = { notifications = it }) },
                )
                ProfileMenuRow(
                    icon = Icons.Outlined.Fingerprint,
                    label = "Acesso por biometria",
                    trailing = { PontoToggle(checked = true, onCheckedChange = {}) },
                )
                ProfileMenuRow(
                    icon = Icons.Outlined.Bedtime,
                    label = "Tema escuro",
                    showDivider = false,
                    trailing = { PontoToggle(checked = darkMode, onCheckedChange = { darkMode = it }) },
                )
            }
        }
    }
}
