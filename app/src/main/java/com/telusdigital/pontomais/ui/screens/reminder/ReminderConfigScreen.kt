package com.telusdigital.pontomais.ui.screens.reminder

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.NotificationsOff
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
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
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.telusdigital.pontomais.R
import com.telusdigital.pontomais.reminder.ReminderConfig
import com.telusdigital.pontomais.ui.components.PontoButton
import com.telusdigital.pontomais.ui.components.PontoTopAppBar
import com.telusdigital.pontomais.ui.theme.Amber
import com.telusdigital.pontomais.ui.theme.AmberContainer
import com.telusdigital.pontomais.ui.theme.Iris
import com.telusdigital.pontomais.ui.theme.Marble
import com.telusdigital.pontomais.ui.theme.Obsidian
import com.telusdigital.pontomais.ui.theme.Pearl
import com.telusdigital.pontomais.ui.theme.PontoMaisTheme
import com.telusdigital.pontomais.ui.theme.Slate
import com.telusdigital.pontomais.ui.theme.TelusPurple

@Composable
fun ReminderConfigScreen(
    onBack: () -> Unit,
    vm: ReminderViewModel = viewModel(),
) {
    val context = LocalContext.current
    val state by vm.state.collectAsState()

    // ── Permission state (AC 6, 7, 8) ────────────────────────────────────────
    var hasPermission by remember { mutableStateOf(notificationsAllowed(context)) }
    var showRationale by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle) {
        val obs = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                hasPermission = notificationsAllowed(context)
            }
        }
        lifecycle.addObserver(obs)
        onDispose { lifecycle.removeObserver(obs) }
    }

    val permLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { granted ->
        hasPermission = granted
        if (granted) {
            vm.setEnabled(true)
        } else {
            // If we can't show rationale anymore and still denied → permanently denied (AC 7).
            val act = (context as? android.app.Activity)
            val canStillAsk = act?.let {
                Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
                    it.shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
            } ?: false
            if (!canStillAsk) showSettingsDialog = true
        }
    }

    Scaffold(
        topBar = {
            PontoTopAppBar(
                title             = stringResource(R.string.reminder_screen_title),
                navigationIcon    = Icons.AutoMirrored.Outlined.ArrowBack,
                onNavigationClick = onBack,
            )
        },
        containerColor = Pearl,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(Modifier.height(12.dp))

            if (state.draft.enabled && !hasPermission) {
                NoPermissionBanner(onOpenSettings = { openAppSettings(context) })
                Spacer(Modifier.height(12.dp))
            }

            // ── Toggle row ───────────────────────────────────────────────
            Surface(
                shape  = RoundedCornerShape(16.dp),
                color  = Color.White,
                border = BorderStroke(1.dp, Marble),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    Surface(shape = RoundedCornerShape(12.dp), color = Iris, modifier = Modifier.size(36.dp)) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = null,
                            tint = TelusPurple,
                            modifier = Modifier.padding(9.dp).size(18.dp),
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text  = stringResource(R.string.reminder_daily_label),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium,
                                color = Obsidian,
                            ),
                        )
                    }
                    Switch(
                        checked = state.draft.enabled,
                        onCheckedChange = { wantsOn ->
                            if (!wantsOn) {
                                vm.setEnabled(false)
                            } else {
                                handleEnableRequest(
                                    context = context,
                                    hasPermission = hasPermission,
                                    onAlreadyGranted = { vm.setEnabled(true) },
                                    onShowRationale = { showRationale = true },
                                    onRequest = { permLauncher.launch(Manifest.permission.POST_NOTIFICATIONS) },
                                )
                            }
                        },
                        colors = SwitchDefaults.colors(
                            checkedTrackColor   = TelusPurple,
                            checkedThumbColor   = Color.White,
                            uncheckedTrackColor = Marble,
                            uncheckedThumbColor = Color.White,
                        ),
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // ── Time card ────────────────────────────────────────────────
            Surface(
                shape  = RoundedCornerShape(16.dp),
                color  = Color.White,
                border = BorderStroke(1.dp, Marble),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text  = stringResource(R.string.reminder_stepper_time_title),
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = Obsidian,
                        ),
                    )
                    Spacer(Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Stepper(
                            value = "%02d".format(state.draft.hour),
                            enabled = state.draft.enabled,
                            onIncrement = { vm.setHour(state.draft.hour + 1) },
                            onDecrement = { vm.setHour(state.draft.hour - 1) },
                        )
                        Box(
                            modifier = Modifier.padding(horizontal = 12.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = ":",
                                style = MaterialTheme.typography.displayMedium.copy(
                                    color = Obsidian,
                                    fontWeight = FontWeight.Medium,
                                ),
                            )
                        }
                        Stepper(
                            value = "%02d".format(state.draft.minute),
                            enabled = state.draft.enabled,
                            onIncrement = { vm.setMinute(state.draft.minute + 1) },
                            onDecrement = { vm.setMinute(state.draft.minute - 1) },
                        )
                    }

                    Spacer(Modifier.height(20.dp))

                    Text(
                        text  = stringResource(R.string.reminder_lead_label),
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = Obsidian,
                        ),
                    )
                    Spacer(Modifier.height(10.dp))

                    LeadChipsRow(
                        selected = state.draft.leadMin,
                        enabled  = state.draft.enabled,
                        onSelect = { vm.setLeadMin(it) },
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            PontoButton(
                label    = stringResource(R.string.reminder_save),
                onClick  = { vm.save(onDone = onBack) },
                modifier = Modifier.fillMaxWidth(),
                enabled  = state.loaded,
            )

            Spacer(Modifier.height(24.dp))
        }
    }

    if (showRationale) {
        AlertDialog(
            onDismissRequest = { showRationale = false },
            title   = { Text(stringResource(R.string.perm_notif_title)) },
            text    = { Text(stringResource(R.string.perm_notif_body)) },
            confirmButton = {
                TextButton(onClick = {
                    showRationale = false
                    permLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }) { Text(stringResource(R.string.perm_notif_allow)) }
            },
            dismissButton = {
                TextButton(onClick = { showRationale = false }) {
                    Text(stringResource(R.string.perm_notif_later))
                }
            },
        )
    }

    if (showSettingsDialog) {
        AlertDialog(
            onDismissRequest = { showSettingsDialog = false },
            title   = { Text(stringResource(R.string.perm_notif_denied_title)) },
            text    = { Text(stringResource(R.string.perm_notif_denied_body)) },
            confirmButton = {
                TextButton(onClick = {
                    showSettingsDialog = false
                    openAppSettings(context)
                }) { Text(stringResource(R.string.perm_notif_open_settings)) }
            },
            dismissButton = {
                TextButton(onClick = { showSettingsDialog = false }) {
                    Text(stringResource(R.string.cancel))
                }
            },
        )
    }
}

@Composable
private fun NoPermissionBanner(onOpenSettings: () -> Unit) {
    Surface(
        shape  = RoundedCornerShape(12.dp),
        color  = AmberContainer,
        border = BorderStroke(1.dp, Marble),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Icon(
                imageVector = Icons.Outlined.NotificationsOff,
                contentDescription = null,
                tint = Amber,
                modifier = Modifier.size(20.dp),
            )
            Text(
                text  = stringResource(R.string.reminder_no_permission_banner),
                style = MaterialTheme.typography.bodySmall.copy(color = Obsidian),
                modifier = Modifier.weight(1f),
            )
            TextButton(onClick = onOpenSettings) {
                Text(
                    text  = stringResource(R.string.reminder_no_permission_cta),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = TelusPurple,
                        fontWeight = FontWeight.SemiBold,
                    ),
                )
            }
        }
    }
}

@Composable
private fun Stepper(
    value: String,
    enabled: Boolean,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
) {
    val alpha = if (enabled) 1f else 0.4f
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        StepperArrow(
            icon    = Icons.Outlined.KeyboardArrowUp,
            cd      = stringResource(R.string.cd_increase),
            enabled = enabled,
            onClick = onIncrement,
        )
        Spacer(Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .size(width = 64.dp, height = 64.dp)
                .background(color = Iris.copy(alpha = alpha), shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text  = value,
                style = MaterialTheme.typography.displayMedium.copy(
                    color = if (enabled) TelusPurple else Slate,
                    fontWeight = FontWeight.Medium,
                ),
            )
        }
        Spacer(Modifier.height(4.dp))
        StepperArrow(
            icon    = Icons.Outlined.KeyboardArrowDown,
            cd      = stringResource(R.string.cd_decrease),
            enabled = enabled,
            onClick = onDecrement,
        )
    }
}

@Composable
private fun StepperArrow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    cd: String,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Surface(
        shape  = RoundedCornerShape(8.dp),
        color  = Color.White,
        border = BorderStroke(1.dp, Marble),
        modifier = Modifier
            .size(width = 56.dp, height = 32.dp)
            .let { if (enabled) it.clickable(onClick = onClick) else it },
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = cd,
                tint = if (enabled) Obsidian else Slate.copy(alpha = 0.5f),
                modifier = Modifier.size(20.dp),
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun LeadChipsRow(
    selected: Int,
    enabled: Boolean,
    onSelect: (Int) -> Unit,
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ReminderConfig.LEAD_OPTIONS.forEach { lead ->
            LeadChip(
                label    = if (lead == 0) stringResource(R.string.reminder_lead_on_time)
                           else stringResource(R.string.reminder_lead_minutes_before, lead),
                selected = lead == selected,
                enabled  = enabled,
                onClick  = { onSelect(lead) },
            )
        }
    }
}

@Composable
private fun LeadChip(
    label: String,
    selected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    val borderColor = if (selected && enabled) TelusPurple else Marble
    val container   = if (selected && enabled) Iris else Color.White
    val contentColor = when {
        !enabled -> Slate.copy(alpha = 0.5f)
        selected -> TelusPurple
        else     -> Obsidian
    }
    Surface(
        shape  = CircleShape,
        color  = container,
        border = BorderStroke(1.dp, borderColor),
        modifier = Modifier
            .height(40.dp)
            .let { if (enabled) it.clickable(onClick = onClick) else it },
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 18.dp),
        ) {
            Text(
                text  = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = contentColor,
                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
                ),
            )
        }
    }
}

// ── Helpers ──────────────────────────────────────────────────────────────────

private fun notificationsAllowed(context: android.content.Context): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true
    return ContextCompat.checkSelfPermission(
        context, Manifest.permission.POST_NOTIFICATIONS,
    ) == PackageManager.PERMISSION_GRANTED
}

private inline fun handleEnableRequest(
    context: android.content.Context,
    hasPermission: Boolean,
    onAlreadyGranted: () -> Unit,
    onShowRationale: () -> Unit,
    onRequest: () -> Unit,
) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
        onAlreadyGranted()
        return
    }
    if (hasPermission) {
        onAlreadyGranted()
        return
    }
    val act = context as? android.app.Activity
    if (act != null && act.shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
        onShowRationale()
    } else {
        onRequest()
    }
}

private fun openAppSettings(context: android.content.Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    context.startActivity(intent)
}

@Preview(showBackground = true, device = "spec:width=412dp,height=892dp")
@Composable
private fun ReminderConfigScreenPreview() {
    PontoMaisTheme {
        ReminderConfigScreen(onBack = {})
    }
}
