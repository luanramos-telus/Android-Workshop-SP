package com.telusdigital.pontomais

import android.app.Application
import com.telusdigital.pontomais.reminder.ReminderNotifier

class PontoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ReminderNotifier.ensureChannel(this)
    }
}
