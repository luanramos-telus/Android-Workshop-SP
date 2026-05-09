package com.telusdigital.pontomais.ui.theme

import androidx.compose.ui.graphics.Color

// ── Ponto+ palette — source of truth: Theming Spec v1.0 ──────────────────────

// 2.1 Primary / brand
val TelusPurple = Color(0xFF4B286D)   // Action primary, app bar, active state, notification accent
val Orchid      = Color(0xFFA020FD)   // Electric accent — hero gradient (orchid → purple), onboarding
val Verbena     = Color(0xFFDFB2FF)   // Mid-tone purple — card accents, badges
val Iris        = Color(0xFFF7ECFF)   // Icon container bg, chip active bg, banners

// 2.2 Success / feedback
val TelusGreen  = Color(0xFF66CC00)   // Confirmation of punch, on-time indicator, success snackbar
val Juniper     = Color(0xFFBAF29E)   // Pale green highlight — extras badge, success badge
val Forest      = Color(0xFF007F4A)   // Primary TELUS green — confirm button alt, links, complete icons
val Hawthorn    = Color(0xFFEAFFE0)   // Pale green wash — success banner bg, juniper icon container
val Moonstone   = Color(0xFFF2F3ED)   // Subtle fills, alternate section bg, menu row hover

// 2.3 Neutrals
val Obsidian    = Color(0xFF222220)   // Primary text, strong typography (clock numbers)
val Slate       = Color(0xFF595956)   // Secondary text, outline icons, captions, metadata
val Pearl       = Color(0xFFFCFDFB)   // Page / scaffold bg — near-white neutral behind cards
val Marble      = Color(0xFFDEE0D9)   // Borders, dividers, switch-off track, subtle fills

// Semantic extras
val ErrorRed            = Color(0xFFBA1A1A)   // color/state/error
val ErrorRedContainer   = Color(0xFFFFDAD6)
val OnErrorRedContainer = Color(0xFF410002)

val Amber          = Color(0xFFA05A00)   // color/state/warning-fg — pause, delay, syncing
val AmberContainer = Color(0xFFFFF1D6)   // color/state/warning-bg — pause/delay icon container

// Dark-scheme companions (v2 roadmap)
val PurpleDark  = Color(0xFF3A1D57)
val SurfaceDark = Color(0xFF26222C)
val OrchidDark  = Color(0xFF5C1E7A)
val DarkPage    = Color(0xFF1A1820)   // Dark-mode scaffold bg
val Galena      = Color(0xFFB6B6B1)   // Dark-mode secondary text
