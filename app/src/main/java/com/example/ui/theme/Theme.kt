package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val KopiNyemilColorScheme = lightColorScheme(
    primary = KopiGreen,
    onPrimary = White,
    primaryContainer = KopiGreenLight,
    onPrimaryContainer = White,
    secondary = CardCyan,
    onSecondary = DarkNeutral,
    secondaryContainer = CardCyanDark,
    onSecondaryContainer = DarkNeutral,
    tertiary = InteractiveAccent,
    onTertiary = White,
    tertiaryContainer = InteractiveAccentLight,
    onTertiaryContainer = DarkNeutral,
    background = KopiGreen,
    onBackground = White,
    surface = KopiGreen,
    onSurface = White,
    surfaceVariant = CardCyan,
    onSurfaceVariant = DarkNeutral,
    error = ErrorRed,
    onError = White
)

@Composable
fun KopiNyemilTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = KopiNyemilColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = KopiGreenDark.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
