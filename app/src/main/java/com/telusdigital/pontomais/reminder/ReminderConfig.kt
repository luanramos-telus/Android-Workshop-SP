package com.telusdigital.pontomais.reminder

data class ReminderConfig(
    val enabled: Boolean = false,
    val hour: Int = 9,
    val minute: Int = 0,
    val leadMin: Int = 5,
) {
    companion object {
        val LEAD_OPTIONS = listOf(0, 5, 10, 15, 30)
    }
}
