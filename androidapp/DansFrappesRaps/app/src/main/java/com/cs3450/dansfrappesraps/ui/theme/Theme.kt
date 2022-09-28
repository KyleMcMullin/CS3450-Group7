package com.cs3450.dansfrappesraps.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = md_theme_dark_primary,
    primaryVariant = md_theme_dark_primaryContainer,
    secondary = md_theme_dark_secondary,
    background = md_theme_dark_background,
    surface = md_theme_dark_surface,
    onPrimary = md_theme_dark_onPrimary,
    onSecondary = md_theme_dark_onSecondary,
    error = md_theme_dark_error,
    onError = md_theme_dark_onError,
    onBackground = md_theme_dark_onBackground,
    onSurface = md_theme_dark_onSurface
)

private val LightColorPalette = lightColors(
    primary = md_theme_light_primary,
    primaryVariant = md_theme_light_primaryContainer,
    secondary = md_theme_light_secondary,
    background = md_theme_light_background,
    surface = md_theme_light_surface,
    onPrimary = md_theme_light_onPrimary,
    onSecondary = md_theme_light_onSecondary,
    onBackground = md_theme_light_onBackground,
    onSurface = md_theme_light_onSurface,
    error = md_theme_light_error,
    onError = md_theme_light_onError
)

@Composable
fun DansFrappesRapsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}