package com.mycompany.currencytracker.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mycompany.currencytracker.data.datastore.StoreUserSetting


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
    userSetting: StoreUserSetting,
    content: @Composable () -> Unit
) {
    val theme = userSetting.userTheme.collectAsState(initial = 0)

    val systemUiController = rememberSystemUiController()


    val colorScheme = if (theme.value == 1) {
        systemUiController.setStatusBarColor(
            color = DarkColorScheme.background
        )
        systemUiController.setNavigationBarColor(
            color = DarkColorScheme.primaryContainer
        )
        DarkColorScheme
    } else if (theme.value == 2){
        systemUiController.setStatusBarColor(
            color = LightColorScheme.background
        )
        systemUiController.setNavigationBarColor(
            color = LightColorScheme.primaryContainer
        )
        LightColorScheme
    }
    else {
        if (isSystemInDarkTheme()){
            systemUiController.setStatusBarColor(
                color = DarkColorScheme.background
            )
            systemUiController.setNavigationBarColor(
                color = DarkColorScheme.primaryContainer
            )
            DarkColorScheme
        }
        else{
            systemUiController.setStatusBarColor(
                color = LightColorScheme.background
            )
            systemUiController.setNavigationBarColor(
                color = LightColorScheme.primaryContainer
            )
            LightColorScheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}