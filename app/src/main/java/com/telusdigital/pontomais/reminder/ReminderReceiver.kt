package com.telusdigital.pontomais.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.telusdigital.pontomais.data.ReminderPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val pending = goAsync()
        val app = context.applicationContext
        val snoozeCount = intent.getIntExtra(ReminderScheduler.EXTRA_SNOOZE_COUNT, 0)

        CoroutineScope(Dispatchers.Default).launch {
            try {
                val cfg = ReminderPreferences(app).config.first()
                val today = LocalDate.now().dayOfWeek
                val isWeekend = today == DayOfWeek.SATURDAY || today == DayOfWeek.SUNDAY

                if (cfg.enabled && !isWeekend) {
                    ReminderNotifier.postReminder(app, cfg, snoozeCount)
                }

                // Always re-arm tomorrow's fresh daily after a count==0 fire (whether or not we posted).
                if (snoozeCount == 0) {
                    ReminderScheduler.rearmNext(app, cfg)
                }
            } finally {
                pending.finish()
            }
        }
    }
}
