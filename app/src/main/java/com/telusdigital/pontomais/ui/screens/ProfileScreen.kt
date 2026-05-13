package com.telusdigital.pontomais.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.telusdigital.pontomais.R
import com.telusdigital.pontomais.data.ReminderPreferences
import com.telusdigital.pontomais.reminder.ReminderConfig
import com.telusdigital.pontomais.ui.components.GradientCard
import com.telusdigital.pontomais.ui.components.PontoBottomNavBar
import com.telusdigital.pontomais.ui.components.PontoTab
import com.telusdigital.pontomais.ui.components.PontoToggle
import com.telusdigital.pontomais.ui.components.PontoTopAppBar
import com.telusdigital.pontomais.ui.components.ProfileMenuRow
import com.telusdigital.pontomais.ui.components.QuickInfoCell
import com.telusdigital.pontomais.ui.theme.ErrorRed
import com.telusdigital.pontomais.ui.theme.Iris
import com.telusdigital.pontomais.ui.theme.Marble
import com.telusdigital.pontomais.ui.theme.Pearl
import com.telusdigital.pontomais.ui.theme.PontoMaisTheme
import com.telusdigital.pontomais.ui.theme.Slate
import com.telusdigital.pontomais.ui.theme.TelusPurple

@Composable
fun ProfileScreen(
    onNavigate: (PontoTab) -> Unit,
    onBack: () -> Unit = {},
    onLogout: () -> Unit = {},
    onOpenReminder: () -> Unit = {},
    currentTab: PontoTab = PontoTab.Profile,
) {
    var notifications by remember { mutableStateOf(true) }
    var biometrics    by remember { mutableStateOf(true) }
    var darkMode      by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val reminderPrefs = remember(context) { ReminderPreferences(context.applicationContext) }
    val reminderConfig by reminderPrefs.config.collectAsState(initial = ReminderConfig())
    val reminderSub = reminderSubtitle(reminderConfig)

    Scaffold(
        topBar = {
            PontoTopAppBar(
                title             = stringResource(R.string.profile_title),
                navigationIcon    = Icons.AutoMirrored.Outlined.ArrowBack,
                onNavigationClick = onBack,
                actions = {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Outlined.Settings, contentDescription = stringResource(R.string.profile_settings_cd))
                    }
                },
            )
        },
        bottomBar = {
            PontoBottomNavBar(currentTab = currentTab, onTabSelected = onNavigate)
        },
        containerColor = Pearl,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
        ) {
            // ── Profile header ────────────────────────────────────────────────
            GradientCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
                Row(
                    modifier          = Modifier.padding(24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Surface(
                        shape  = CircleShape,
                        color  = Color.White,
                        modifier = Modifier.size(72.dp),
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text  = "LR",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    color = TelusPurple,
                                    fontWeight = FontWeight.Bold,
                                ),
                            )
                        }
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text  = "Luan Ramos",
                            style = MaterialTheme.typography.titleLarge.copy(color = Color.White),
                        )
                        Text(
                            text  = "Sr. Software Engineer",
                            style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.78f)),
                        )
                        Spacer(Modifier.height(4.dp))
                        Surface(
                            shape = CircleShape,
                            color = Color.White,
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Info,
                                    contentDescription = null,
                                    tint = TelusPurple,
                                    modifier = Modifier.size(11.dp),
                                )
                                Text(
                                    text  = "TELUS Digital · São Paulo",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = TelusPurple,
                                        fontWeight = FontWeight.SemiBold,
                                    ),
                                )
                            }
                        }
                    }
                }
            }

            // ── Quick info grid ───────────────────────────────────────────────
            Row(
                modifier              = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                QuickInfoCell(label = "Matrícula", value = "TD-48217",   modifier = Modifier.weight(1f))
                QuickInfoCell(label = "Admissão",  value = "12 mar 2022", modifier = Modifier.weight(1f))
                QuickInfoCell(label = "Equipe",    value = "Design Ops", modifier = Modifier.weight(1f))
            }

            Spacer(Modifier.height(12.dp))

            // ── Sections ──────────────────────────────────────────────────────
            MenuSection(title = stringResource(R.string.profile_section_my_punch)) {
                ProfileMenuRow(icon = Icons.Outlined.Edit,     label = stringResource(R.string.profile_request_adjust), sub = stringResource(R.string.profile_request_adjust_sub))
                ProfileMenuRow(icon = Icons.Outlined.CalendarMonth, label = stringResource(R.string.profile_request_excuse),  sub = stringResource(R.string.profile_request_excuse_sub))
                ProfileMenuRow(icon = Icons.AutoMirrored.Outlined.Logout, label = stringResource(R.string.profile_punch_mirror),  sub = stringResource(R.string.profile_punch_mirror_sub), showDivider = false)
            }

            Spacer(Modifier.height(4.dp))

            MenuSection(title = stringResource(R.string.profile_section_reminders)) {
                ProfileMenuRow(
                    icon = Icons.Outlined.NotificationsActive,
                    label = stringResource(R.string.profile_reminder_label),
                    sub = reminderSub,
                    showDivider = false,
                    onClick = onOpenReminder,
                )
            }

            Spacer(Modifier.height(4.dp))

            MenuSection(title = stringResource(R.string.profile_section_account)) {
                ProfileMenuRow(icon = Icons.Outlined.Person, label = stringResource(R.string.profile_personal_data))
                ProfileMenuRow(
                    icon = Icons.Outlined.Notifications,
                    label = stringResource(R.string.profile_notifications),
                    trailing = { PontoToggle(checked = notifications, onCheckedChange = { notifications = it }) },
                )
                ProfileMenuRow(
                    icon = Icons.Outlined.Fingerprint,
                    label = stringResource(R.string.profile_biometric),
                    trailing = { PontoToggle(checked = biometrics, onCheckedChange = { biometrics = it }) },
                )
                ProfileMenuRow(
                    icon = Icons.Outlined.Bedtime,
                    label = stringResource(R.string.profile_dark_mode),
                    showDivider = false,
                    trailing = { PontoToggle(checked = darkMode, onCheckedChange = { darkMode = it }) },
                )
            }

            Spacer(Modifier.height(4.dp))

            MenuSection(title = stringResource(R.string.profile_section_support)) {
                ProfileMenuRow(icon = Icons.AutoMirrored.Outlined.HelpOutline, label = stringResource(R.string.profile_help_center))
                ProfileMenuRow(
                    icon = Icons.Outlined.Info,
                    label = stringResource(R.string.profile_about),
                    sub = "v2.4.1 (build 2026.05)",
                    showDivider = false,
                )
            }

            Spacer(Modifier.height(8.dp))

            // ── Logout ────────────────────────────────────────────────────────
            OutlinedButton(
                onClick = onLogout,
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Marble),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorRed),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(52.dp),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Logout,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                )
                Text(
                    text  = "  ${stringResource(R.string.profile_logout)}",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                )
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun reminderSubtitle(cfg: ReminderConfig): String {
    val timeStr = "%02d:%02d".format(cfg.hour, cfg.minute)
    return when {
        !cfg.enabled       -> stringResource(R.string.profile_reminder_summary_off)
        cfg.leadMin == 0   -> stringResource(R.string.profile_reminder_summary_on_time, timeStr)
        else               -> stringResource(R.string.profile_reminder_summary_active, cfg.leadMin, timeStr)
    }
}

@Composable
private fun MenuSection(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text  = title.uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(
                color = Slate,
                letterSpacing = androidx.compose.ui.unit.TextUnit(0.08f, androidx.compose.ui.unit.TextUnitType.Em),
            ),
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp),
        )
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            border = androidx.compose.foundation.BorderStroke(1.dp, Marble),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column { content() }
        }
    }
}

@Preview(showBackground = true, device = "spec:width=412dp,height=892dp")
@Composable
private fun ProfileScreenPreview() {
    PontoMaisTheme {
        ProfileScreen(onNavigate = {})
    }
}
