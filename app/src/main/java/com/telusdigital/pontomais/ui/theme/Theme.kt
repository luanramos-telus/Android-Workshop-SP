package com.telusdigital.pontomais.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Spec §2.4 — semantic aliases mapped to M3 color roles
private val LightColorScheme = lightColorScheme(
    primary            = TelusPurple,
    onPrimary          = Color.White,          // color/text/on-primary
    primaryContainer   = Iris,                 // color/bg/accent-soft
    onPrimaryContainer = TelusPurple,          // color/text/on-accent

    secondary            = Orchid,
    onSecondary          = Color.White,
    secondaryContainer   = Verbena,
    onSecondaryContainer = Obsidian,

    tertiary            = Forest,              // color/state/success
    onTertiary          = Color.White,
    tertiaryContainer   = Juniper,             // color/bg/success-soft (via Hawthorn alias)
    onTertiaryContainer = Obsidian,

    error            = ErrorRed,
    onError          = Color.White,
    errorContainer   = ErrorRedContainer,
    onErrorContainer = OnErrorRedContainer,

    background       = Pearl,                  // color/bg/page — scaffold
    onBackground     = Obsidian,               // color/text/primary
    surface          = Color.White,            // color/bg/surface — cards
    onSurface        = Obsidian,
    surfaceVariant   = Moonstone,
    onSurfaceVariant = Slate,                  // color/text/secondary
    outline          = Marble,                 // color/border
    outlineVariant   = Marble,
    scrim            = Obsidian,
    inverseSurface   = Obsidian,
    inverseOnSurface = Pearl,
    inversePrimary   = Iris,
)

private val DarkColorScheme = darkColorScheme(
    primary            = Verbena,
    onPrimary          = TelusPurple,
    primaryContainer   = PurpleDark,
    onPrimaryContainer = Iris,

    secondary            = Verbena,
    onSecondary          = Obsidian,
    secondaryContainer   = OrchidDark,
    onSecondaryContainer = Verbena,

    tertiary            = TelusGreen,
    onTertiary          = Obsidian,
    tertiaryContainer   = Forest,
    onTertiaryContainer = Juniper,

    error            = ErrorRed,
    onError          = Color.White,
    errorContainer   = ErrorRedContainer,
    onErrorContainer = OnErrorRedContainer,

    background       = DarkPage,
    onBackground     = Pearl,
    surface          = SurfaceDark,
    onSurface        = Pearl,
    surfaceVariant   = SurfaceDark,
    onSurfaceVariant = Galena,
    outline          = Marble,
    outlineVariant   = SurfaceDark,
    inverseSurface   = Pearl,
    inverseOnSurface = Obsidian,
    inversePrimary   = TelusPurple,
)

@Composable
fun PontoMaisTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography  = Typography,
        content     = content,
    )
}
