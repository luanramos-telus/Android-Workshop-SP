package com.telusdigital.pontomais.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.telusdigital.pontomais.MainActivity
import com.telusdigital.pontomais.data.ReminderPreferences
import kotlinx.coroutines.flow.first
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

object ReminderScheduler {

    const val EXTRA_SNOOZE_COUNT = "snooze_count"

    private const val RC_DAILY  = 0xCAFE
    private const val RC_SNOOZE = 0xCAFF
    private const val RC_OPEN_APP = 0xCAFD

    private val PI_FLAGS = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

    /** Cancel any pending alarm and (if enabled) arm the next valid fire. */
    suspend fun reschedule(context: Context) {
        val app = context.applicationContext
        val cfg = ReminderPreferences(app).config.first()
        cancelAll(app)
        if (cfg.enabled) armDaily(app, cfg, skipToday = false)
    }

    /** Convenience overload used when the caller already has a fresh config. */
    fun reschedule(context: Context, cfg: ReminderConfig) {
        val app = context.applicationContext
        cancelAll(app)
        if (cfg.enabled) armDaily(app, cfg, skipToday = false)
    }

    /** Called when the user punches before today's reminder would fire. */
    suspend fun cancelToday(context: Context) {
        val app = context.applicationContext
        val cfg = ReminderPreferences(app).config.first()
        cancelSnooze(app)
        cancelDaily(app)
        if (cfg.enabled) armDaily(app, cfg, skipToday = true)
    }

    /** Snooze receiver: arm the daily-fire PI for `now + 5 min`, carrying the new snooze count. */
    fun snooze(context: Context, newCount: Int) {
        val app = context.applicationContext
        val fireAt = System.currentTimeMillis() + 5 * 60 * 1000L
        val intent = Intent(app, ReminderReceiver::class.java).apply {
            putExtra(EXTRA_SNOOZE_COUNT, newCount)
        }
        val pi = PendingIntent.getBroadcast(app, RC_SNOOZE, intent, PI_FLAGS)
        setAlarmSafely(app, fireAt, pi)
    }

    /** Re-arm tomorrow's daily after a count==0 fire. */
    fun rearmNext(context: Context, cfg: ReminderConfig) {
        if (!cfg.enabled) return
        armDaily(context.applicationContext, cfg, skipToday = true)
    }

    fun cancelSnooze(context: Context) {
        val app = context.applicationContext
        val intent = Intent(app, ReminderReceiver::class.java)
        val pi = PendingIntent.getBroadcast(app, RC_SNOOZE, intent, PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE)
        if (pi != null) {
            (app.getSystemService(Context.ALARM_SERVICE) as AlarmManager).cancel(pi)
            pi.cancel()
        }
    }

    private fun cancelDaily(context: Context) {
        val app = context.applicationContext
        val intent = Intent(app, ReminderReceiver::class.java)
        val pi = PendingIntent.getBroadcast(app, RC_DAILY, intent, PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE)
        if (pi != null) {
            (app.getSystemService(Context.ALARM_SERVICE) as AlarmManager).cancel(pi)
            pi.cancel()
        }
    }

    private fun cancelAll(context: Context) {
        cancelDaily(context)
        cancelSnooze(context)
    }

    private fun armDaily(context: Context, cfg: ReminderConfig, skipToday: Boolean) {
        val fireAt = nextFireMillis(cfg, LocalDateTime.now(), skipToday)
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra(EXTRA_SNOOZE_COUNT, 0)
        }
        val pi = PendingIntent.getBroadcast(context, RC_DAILY, intent, PI_FLAGS)
        setAlarmSafely(context, fireAt, pi)
    }

    /**
     * Try `setAlarmClock` (best for user-scheduled reminders — shows the system alarm icon and
     * bypasses Doze). On OEM builds that revoke exact-alarm permission unexpectedly, fall back to
     * `setWindow` so we never crash and never silently drop a reminder.
     */
    private fun setAlarmSafely(context: Context, fireAtMillis: Long, pi: PendingIntent) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        try {
            am.setAlarmClock(AlarmManager.AlarmClockInfo(fireAtMillis, openAppPendingIntent(context)), pi)
        } catch (_: SecurityException) {
            // OEM denied exact alarms — best-effort inexact fallback (1-min slack window).
            am.setWindow(AlarmManager.RTC_WAKEUP, fireAtMillis, 60_000L, pi)
        }
    }

    internal fun nextFireMillis(cfg: ReminderConfig, now: LocalDateTime, skipToday: Boolean): Long {
        // target time = HH:MM minus leadMin
        var target = LocalDate.now().atTime(cfg.hour, cfg.minute).minusMinutes(cfg.leadMin.toLong())
        if (skipToday || !target.isAfter(now)) target = target.plusDays(1)
        while (target.dayOfWeek == DayOfWeek.SATURDAY || target.dayOfWeek == DayOfWeek.SUNDAY) {
            target = target.plusDays(1)
        }
        return target.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    private fun openAppPendingIntent(context: Context): PendingIntent {
        val launch = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        return PendingIntent.getActivity(context, RC_OPEN_APP, launch, PI_FLAGS)
    }
}
