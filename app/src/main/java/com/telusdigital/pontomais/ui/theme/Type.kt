package com.telusdigital.pontomais.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.telusdigital.pontomais.R

// ── Brand font families (Theming Spec §03) ────────────────────────────────────

val HnTelusSa = FontFamily(
    Font(R.font.hn_telus_sa,    FontWeight.Normal),
    Font(R.font.hn_telus_sa_it, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.hn_telus_sa_md, FontWeight.Medium),
    Font(R.font.hn_telus_sa_bd, FontWeight.Bold),
)

val HnTelusSaDisplay = FontFamily(
    Font(R.font.hn_telus_sa_display,    FontWeight.Normal),
    Font(R.font.hn_telus_sa_display_md, FontWeight.Medium),
    Font(R.font.hn_telus_sa_display_bd, FontWeight.Bold),
)

// ── Type scale — Theming Spec §03 ─────────────────────────────────────────────
//
// Display/Hero  64/400  −3.5%   clock, balance hero
// Display/H1    36/500  −2%     greeting, big numbers
// Display/H2    22/500  −1.5%   screen section titles
// Display/Num   20/500   0      times in punches, time-picker chips
// Body/Default  15/500   0  lh1.5   menu text, card copy
// Body/Sub      13/400   0        subtitles, menu row hints
// Caption       12/400   0        dates, units, metadata
// Eyebrow       11/700  +8%  UC  section overlines
// Button        14/600  +1%      filled / text buttons
//
// Mapping to M3 Typography slots:
//   displayLarge  → Hero clock (64)
//   displayMedium → H1 greeting (36)
//   displaySmall  → H2 screen titles (22)
//   headlineLarge → Display/Num punch times (20)
//   headlineMedium / headlineSmall → (reserved / system use)
//   titleLarge    → (system use, e.g. dialog titles)
//   titleMedium   → Body/Default menu rows (15)
//   titleSmall    → Button (14/600)
//   bodyLarge     → Body/Default body copy (15)
//   bodyMedium    → Body/Sub (13)
//   bodySmall     → Caption (12)
//   labelLarge    → Button (14/600)
//   labelMedium   → Eyebrow section title (11/700 + UC handled at call site)
//   labelSmall    → (11 fallback)

val Typography = Typography(
    // ── Display ──────────────────────────────────────────────────────────────
    displayLarge = TextStyle(
        fontFamily    = HnTelusSaDisplay,
        fontWeight    = FontWeight.Normal,
        fontSize      = 64.sp,
        lineHeight    = 64.sp,
        letterSpacing = (-2.24).sp,   // −3.5% of 64
    ),
    displayMedium = TextStyle(
        fontFamily    = HnTelusSaDisplay,
        fontWeight    = FontWeight.Medium,
        fontSize      = 36.sp,
        lineHeight    = 37.8.sp,      // 1.05 × 36
        letterSpacing = (-0.72).sp,   // −2%
    ),
    displaySmall = TextStyle(
        fontFamily    = HnTelusSaDisplay,
        fontWeight    = FontWeight.Medium,
        fontSize      = 22.sp,
        lineHeight    = 25.3.sp,      // 1.15 × 22
        letterSpacing = (-0.33).sp,   // −1.5%
    ),

    // ── Headline — punch times, numeric display ───────────────────────────────
    headlineLarge = TextStyle(
        fontFamily    = HnTelusSaDisplay,
        fontWeight    = FontWeight.Medium,
        fontSize      = 20.sp,
        lineHeight    = 24.sp,
        letterSpacing = 0.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily    = HnTelusSaDisplay,
        fontWeight    = FontWeight.Medium,
        fontSize      = 18.sp,
        lineHeight    = 22.sp,
        letterSpacing = 0.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily    = HnTelusSaDisplay,
        fontWeight    = FontWeight.Medium,
        fontSize      = 16.sp,
        lineHeight    = 20.sp,
        letterSpacing = 0.sp,
    ),

    // ── Title ─────────────────────────────────────────────────────────────────
    titleLarge = TextStyle(
        fontFamily    = HnTelusSa,
        fontWeight    = FontWeight.Medium,
        fontSize      = 22.sp,
        lineHeight    = 28.sp,
        letterSpacing = 0.sp,
    ),
    titleMedium = TextStyle(
        fontFamily    = HnTelusSa,
        fontWeight    = FontWeight.Medium,
        fontSize      = 15.sp,
        lineHeight    = 22.5.sp,      // 1.5 × 15
        letterSpacing = 0.sp,
    ),
    titleSmall = TextStyle(
        fontFamily    = HnTelusSa,
        fontWeight    = FontWeight.Medium,
        fontSize      = 14.sp,
        lineHeight    = 20.sp,
        letterSpacing = 0.14.sp,      // +1%
    ),

    // ── Body ──────────────────────────────────────────────────────────────────
    bodyLarge = TextStyle(
        fontFamily    = HnTelusSa,
        fontWeight    = FontWeight.Medium,
        fontSize      = 15.sp,
        lineHeight    = 22.5.sp,
        letterSpacing = 0.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily    = HnTelusSa,
        fontWeight    = FontWeight.Normal,
        fontSize      = 13.sp,
        lineHeight    = 18.sp,
        letterSpacing = 0.sp,
    ),
    bodySmall = TextStyle(
        fontFamily    = HnTelusSa,
        fontWeight    = FontWeight.Normal,
        fontSize      = 12.sp,
        lineHeight    = 16.sp,
        letterSpacing = 0.sp,
    ),

    // ── Label ─────────────────────────────────────────────────────────────────
    labelLarge = TextStyle(
        fontFamily    = HnTelusSa,
        fontWeight    = FontWeight.SemiBold,
        fontSize      = 14.sp,
        lineHeight    = 20.sp,
        letterSpacing = 0.14.sp,      // +1%
    ),
    labelMedium = TextStyle(
        fontFamily    = HnTelusSa,
        fontWeight    = FontWeight.Bold,
        fontSize      = 11.sp,
        lineHeight    = 16.sp,
        letterSpacing = 0.88.sp,      // +8%
    ),
    labelSmall = TextStyle(
        fontFamily    = HnTelusSa,
        fontWeight    = FontWeight.Medium,
        fontSize      = 11.sp,
        lineHeight    = 16.sp,
        letterSpacing = 0.55.sp,
    ),
)
