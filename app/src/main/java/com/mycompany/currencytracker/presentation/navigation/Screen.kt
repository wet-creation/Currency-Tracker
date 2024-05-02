package com.mycompany.currencytracker.presentation.navigation

import com.mycompany.currencytracker.presentation.navigation.NavigationRoutes.CALCULATOR_SCREEN
import com.mycompany.currencytracker.presentation.navigation.NavigationRoutes.COIN_DETAILS_SCREEN
import com.mycompany.currencytracker.presentation.navigation.NavigationRoutes.FIAT_DETAILS_SCREEN
import com.mycompany.currencytracker.presentation.navigation.NavigationRoutes.LOGIN_SCREEN
import com.mycompany.currencytracker.presentation.navigation.NavigationRoutes.NOTIFICATION_SCREEN
import com.mycompany.currencytracker.presentation.navigation.NavigationRoutes.NOTIFICATION_SCREEN_LIST
import com.mycompany.currencytracker.presentation.navigation.NavigationRoutes.NOTIFICATION_SCREEN_SELECT
import com.mycompany.currencytracker.presentation.navigation.NavigationRoutes.PROFILE_SCREEN
import com.mycompany.currencytracker.presentation.navigation.NavigationRoutes.REGISTER_SCREEN
import com.mycompany.currencytracker.presentation.navigation.NavigationRoutes.SELECT_MAIN_CURRENCY_SCREEN
import com.mycompany.currencytracker.presentation.navigation.NavigationRoutes.SETTINGS_SCREEN


sealed class Screen(val route: String) {
    object NotificationScreen: Screen(NOTIFICATION_SCREEN)
    object NotificationScreenList: Screen(NOTIFICATION_SCREEN_LIST)
    object NotificationScreenSelect: Screen(NOTIFICATION_SCREEN_SELECT)
    object SettingScreen: Screen(SETTINGS_SCREEN)
    object SelectMainCurrencyScreen: Screen(SELECT_MAIN_CURRENCY_SCREEN)
    object CalculatorScreen: Screen(CALCULATOR_SCREEN)
    object CurrencyDetailScreen: Screen(FIAT_DETAILS_SCREEN)
    object CryptoDetailScreen: Screen(COIN_DETAILS_SCREEN)
    object LoginScreen: Screen(LOGIN_SCREEN)
    object RegisterScreen: Screen(REGISTER_SCREEN)
    object ProfileScreen: Screen(PROFILE_SCREEN)
}