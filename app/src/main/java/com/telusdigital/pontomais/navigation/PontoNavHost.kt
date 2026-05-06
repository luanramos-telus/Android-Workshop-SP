package com.telusdigital.pontomais.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.telusdigital.pontomais.ui.components.PontoTab
import com.telusdigital.pontomais.ui.screens.BankScreen
import com.telusdigital.pontomais.ui.screens.HistoryScreen
import com.telusdigital.pontomais.ui.screens.HomeScreen
import com.telusdigital.pontomais.ui.screens.LoginScreen
import com.telusdigital.pontomais.ui.screens.ProfileScreen

object Routes {
    const val Login   = "login"
    const val Home    = "home"
    const val History = "history"
    const val Bank    = "bank"
    const val Profile = "profile"
}

private fun PontoTab.route() = when (this) {
    PontoTab.Home    -> Routes.Home
    PontoTab.History -> Routes.History
    PontoTab.Bank    -> Routes.Bank
    PontoTab.Profile -> Routes.Profile
}

@Composable
fun PontoNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController    = navController,
        startDestination = Routes.Login,
        modifier         = modifier,
    ) {
        composable(Routes.Login) {
            LoginScreen(onLogin = { navController.navigate(Routes.Home) })
        }

        composable(Routes.Home) {
            HomeScreen(
                onNavigate  = { tab -> navController.navigate(tab.route()) },
                currentTab  = PontoTab.Home,
            )
        }

        composable(Routes.History) {
            HistoryScreen(
                onNavigate = { tab -> navController.navigate(tab.route()) },
                onBack     = { navController.popBackStack() },
                currentTab = PontoTab.History,
            )
        }

        composable(Routes.Bank) {
            BankScreen(
                onNavigate = { tab -> navController.navigate(tab.route()) },
                onBack     = { navController.popBackStack() },
                currentTab = PontoTab.Bank,
            )
        }

        composable(Routes.Profile) {
            ProfileScreen(
                onNavigate = { tab -> navController.navigate(tab.route()) },
                onBack     = { navController.popBackStack() },
                onLogout   = {
                    navController.navigate(Routes.Login) {
                        popUpTo(Routes.Login) { inclusive = true }
                    }
                },
                currentTab = PontoTab.Profile,
            )
        }
    }
}
