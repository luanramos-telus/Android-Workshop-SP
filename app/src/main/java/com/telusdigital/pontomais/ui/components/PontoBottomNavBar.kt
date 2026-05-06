package com.telusdigital.pontomais.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.telusdigital.pontomais.ui.theme.Iris
import com.telusdigital.pontomais.ui.theme.Marble
import com.telusdigital.pontomais.ui.theme.Pearl
import com.telusdigital.pontomais.ui.theme.PontoMaisTheme
import com.telusdigital.pontomais.ui.theme.Slate
import com.telusdigital.pontomais.ui.theme.TelusPurple

enum class PontoTab(
    val label: String,
    val icon: ImageVector,
    val route: String,
) {
    Home("Início", Icons.Outlined.Home, "home"),
    History("Histórico", Icons.Outlined.History, "history"),
    Bank("Banco", Icons.Outlined.AccountBalance, "bank"),
    Profile("Perfil", Icons.Outlined.Person, "profile"),
}

@Composable
fun PontoBottomNavBar(
    currentTab: PontoTab,
    onTabSelected: (PontoTab) -> Unit,
) {
    NavigationBar(
        containerColor = Pearl,
        tonalElevation  = androidx.compose.ui.unit.Dp.Unspecified,
    ) {
        PontoTab.entries.forEach { tab ->
            val selected = tab == currentTab
            NavigationBarItem(
                selected = selected,
                onClick  = { onTabSelected(tab) },
                icon = {
                    Icon(imageVector = tab.icon, contentDescription = tab.label)
                },
                label = {
                    Text(
                        text  = tab.label,
                        style = MaterialTheme.typography.labelSmall,
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor   = TelusPurple,
                    selectedTextColor   = TelusPurple,
                    indicatorColor      = Iris,
                    unselectedIconColor = Slate,
                    unselectedTextColor = Slate,
                ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PontoBottomNavBarPreview() {
    PontoMaisTheme {
        PontoBottomNavBar(
            currentTab = PontoTab.Home,
            onTabSelected = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PontoBottomNavBarHistoryPreview() {
    PontoMaisTheme {
        PontoBottomNavBar(
            currentTab = PontoTab.History,
            onTabSelected = {},
        )
    }
}
