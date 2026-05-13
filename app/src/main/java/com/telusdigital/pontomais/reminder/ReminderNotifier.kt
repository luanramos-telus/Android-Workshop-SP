package com.telusdigital.pontomais.reminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.telusdigital.pontomais.MainActivity
import com.telusdigital.pontomais.R
import com.telusdigital.pontomais.ui.theme.TelusPurple

object ReminderNotifier {

    const val CHANNEL_ID = "reminder_punch_v1"
    const val NOTIF_ID   = 1001

    /** Set on the launch intent when the user comes in via the notification body or action. */
    const val EXTRA_FROM_REMINDER = "from_reminder"

    private const val RC_CONTENT       = 0xCB00
    private const val RC_ACTION_PUNCH  = 0xCB01
    private const val RC_ACTION_SNOOZE = 0xCB02
    private val PI_FLAGS = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

    /** Called from PontoApp.onCreate(). Channels are idempotent. */
    fun ensureChannel(context: Context) {
        val mgr = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (mgr.getNotificationChannel(CHANNEL_ID) != null) return
        val channel = NotificationChannel(
            CHANNEL_ID,
            context.getString(R.string.notif_channel_name),
            NotificationManager.IMPORTANCE_HIGH,
        ).apply {
            description = context.getString(R.string.notif_channel_desc)
            // No setBypassDnd → respects Do Not Disturb (AC 18).
        }
        mgr.createNotificationChannel(channel)
    }

    fun postReminder(context: Context, cfg: ReminderConfig, snoozeCount: Int) {
        val app = context.applicationContext
        ensureChannel(app)

        val timeStr = "%02d:%02d".format(cfg.hour, cfg.minute)
        val title = if (cfg.leadMin > 0) {
            app.getString(R.string.notif_title_lead, cfg.leadMin)
        } else {
            app.getString(R.string.notif_title_now)
        }
        val body = app.getString(R.string.notif_body, timeStr)

        val punchAction = NotificationCompat.Action.Builder(
            0,
            app.getString(R.string.notif_action_punch),
            openAppPendingIntent(app, RC_ACTION_PUNCH),
        ).build()

        val builder = NotificationCompat.Builder(app, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notif_bell)
            .setColor(TelusPurple.toArgb())
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setContentIntent(openAppPendingIntent(app, RC_CONTENT))
            .setAutoCancel(true)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(punchAction)

        if (snoozeCount < 2) {
            val snoozeAction = NotificationCompat.Action.Builder(
                0,
                app.getString(R.string.notif_action_snooze),
                snoozeActionPendingIntent(app, snoozeCount + 1),
            ).build()
            builder.addAction(snoozeAction)
        } else {
            // After the 2nd snooze → ongoing & not swipe-dismissible (AC 16).
            builder.setOngoing(true).setAutoCancel(false)
        }

        try {
            NotificationManagerCompat.from(app).notify(NOTIF_ID, builder.build())
        } catch (_: SecurityException) {
            // POST_NOTIFICATIONS revoked between scheduling and firing — nothing to do.
        }
    }

    fun dismiss(context: Context) {
        NotificationManagerCompat.from(context.applicationContext).cancel(NOTIF_ID)
    }

    /**
     * Direct-to-Activity PendingIntent. Android 12+ blocks notification trampolines —
     * tapping a notification or action whose PendingIntent routes through a BroadcastReceiver
     * that then calls startActivity will be silently dropped. So both the body tap and the
     * "Bater ponto" action launch MainActivity directly; MainActivity reads
     * EXTRA_FROM_REMINDER and cancels today's alarm in onCreate/onNewIntent.
     */
    private fun openAppPendingIntent(context: Context, requestCode: Int): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(EXTRA_FROM_REMINDER, true)
        }
        return PendingIntent.getActivity(context, requestCode, intent, PI_FLAGS)
    }

    private fun snoozeActionPendingIntent(context: Context, newCount: Int): PendingIntent {
        val intent = Intent(context, SnoozeReceiver::class.java).apply {
            putExtra(ReminderScheduler.EXTRA_SNOOZE_COUNT, newCount)
        }
        // Use newCount in the RC so PI extras aren't collapsed across snoozes.
        return PendingIntent.getBroadcast(context, RC_ACTION_SNOOZE + newCount, intent, PI_FLAGS)
    }

}
