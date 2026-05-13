package com.telusdigital.pontomais

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.telusdigital.pontomais.navigation.PontoNavHost
import com.telusdigital.pontomais.reminder.ReminderNotifier
import com.telusdigital.pontomais.reminder.ReminderScheduler
import com.telusdigital.pontomais.ui.theme.PontoMaisTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        handleReminderIntent(intent)
        setContent {
            PontoMaisTheme {
                PontoNavHost()
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleReminderIntent(intent)
    }

    private fun handleReminderIntent(intent: Intent?) {
        if (intent?.getBooleanExtra(ReminderNotifier.EXTRA_FROM_REMINDER, false) != true) return
        ReminderNotifier.dismiss(this)
        lifecycleScope.launch { ReminderScheduler.cancelToday(this@MainActivity) }
    }
}
