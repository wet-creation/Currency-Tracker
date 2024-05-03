package com.mycompany.currencytracker.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val DarkColorScheme = darkColorScheme(
    primaryContainer = ThemeColors.Dark.primaryContainer,
    background = ThemeColors.Dark.background,
    primary = ThemeColors.Dark.primary,
    secondary = ThemeColors.Dark.secondary,
    outline = ThemeColors.Dark.outline,
    surfaceVariant = ThemeColors.Dark.surfaceVariant,
    secondaryContainer = ThemeColors.Dark.secondaryContainer,
    surface = ThemeColors.Dark.primaryContainer
)

private val LightColorScheme = lightColorScheme(
    primaryContainer = ThemeColors.Light.primaryContainer,
    background = ThemeColors.Light.background,
    primary = ThemeColors.Light.primary,
    secondary = ThemeColors.Light.secondary,
    outline = ThemeColors.Light.outline,
    surfaceVariant = ThemeColors.Light.surfaceVariant,
    secondaryContainer = ThemeColors.Light.secondaryContainer,
    surface = ThemeColors.Light.primaryContainer
)

@Composable
fun CurrencyTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }



    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}