package com.telusdigital.pontomais.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SnoozeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val pending = goAsync()
        val app = context.applicationContext
        val newCount = intent.getIntExtra(ReminderScheduler.EXTRA_SNOOZE_COUNT, 1).coerceIn(1, 2)

        CoroutineScope(Dispatchers.Default).launch {
            try {
                ReminderNotifier.dismiss(app)
                ReminderScheduler.snooze(app, newCount)
            } finally {
                pending.finish()
            }
        }
    }
}
