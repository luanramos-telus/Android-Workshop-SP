package com.telusdigital.pontomais.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary            = TelusPurple,
    onPrimary          = Color.White,
    primaryContainer   = Iris,
    onPrimaryContainer = TelusPurple,

    secondary            = Orchid,
    onSecondary          = Color.White,
    secondaryContainer   = Verbena,
    onSecondaryContainer = Obsidian,

    tertiary            = Forest,
    onTertiary          = Color.White,
    tertiaryContainer   = Juniper,
    onTertiaryContainer = Obsidian,

    error            = ErrorRed,
    onError          = Color.White,
    errorContainer   = ErrorRedContainer,
    onErrorContainer = OnErrorRedContainer,

    background        = Pearl,
    onBackground      = Obsidian,
    surface           = Pearl,
    onSurface         = Obsidian,
    surfaceVariant    = Moonstone,
    onSurfaceVariant  = Slate,
    outline           = Marble,
    outlineVariant    = Marble,
    scrim             = Obsidian,
    inverseSurface    = Obsidian,
    inverseOnSurface  = Pearl,
    inversePrimary    = Iris,
)

private val DarkColorScheme = darkColorScheme(
    primary            = Verbena,
    onPrimary          = TelusPurple,
    primaryContainer   = TelusPurple,
    onPrimaryContainer = Iris,

    secondary            = Verbena,
    onSecondary          = Obsidian,
    secondaryContainer   = OrchidDark,
    onSecondaryContainer = Verbena,

    tertiary            = Juniper,
    onTertiary          = Color(0xFF003920),
    tertiaryContainer   = Forest,
    onTertiaryContainer = Juniper,

    error            = ErrorRed,
    onError          = Color.White,
    errorContainer   = ErrorRedContainer,
    onErrorContainer = OnErrorRedContainer,

    background       = Obsidian,
    onBackground     = Pearl,
    surface          = Obsidian,
    onSurface        = Pearl,
    surfaceVariant   = SurfaceDark,
    onSurfaceVariant = Galena,
    outline          = Galena,
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
