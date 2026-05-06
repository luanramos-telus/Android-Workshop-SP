package com.telusdigital.pontomais.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.telusdigital.pontomais.ui.theme.PontoMaisTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PontoTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    // Pass either an icon (back arrow) or a fully custom composable (avatar).
    navigationIcon: ImageVector? = null,
    leadingContent: @Composable (() -> Unit)? = null,
    onNavigationClick: () -> Unit = {},
    actions: @Composable () -> Unit = {},
) {
    TopAppBar(
        title = {
            Text(
                text  = title,
                style = MaterialTheme.typography.titleLarge,
            )
        },
        modifier = modifier,
        navigationIcon = {
            when {
                leadingContent != null -> leadingContent()
                navigationIcon != null -> IconButton(onClick = onNavigationClick) {
                    Icon(imageVector = navigationIcon, contentDescription = "Voltar")
                }
            }
        },
        actions = { actions() },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor          = MaterialTheme.colorScheme.background,
            titleContentColor       = MaterialTheme.colorScheme.onBackground,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor  = MaterialTheme.colorScheme.onBackground,
        ),
    )
}

@Composable
fun NotificationIconButton(badgeCount: Int = 0, onClick: () -> Unit = {}) {
    IconButton(onClick = onClick) {
        BadgedBox(
            badge = {
                if (badgeCount > 0) Badge { Text(badgeCount.toString()) }
            },
        ) {
            Icon(imageVector = Icons.Outlined.Notifications, contentDescription = "Notificações")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PontoTopAppBarPreview() {
    PontoMaisTheme {
        PontoTopAppBar(
            title   = "Olá, Ana",
            actions = { NotificationIconButton(badgeCount = 2) },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PontoTopAppBarWithBackPreview() {
    PontoMaisTheme {
        PontoTopAppBar(
            title          = "Histórico",
            navigationIcon = Icons.AutoMirrored.Outlined.ArrowBack,
        )
    }
}
