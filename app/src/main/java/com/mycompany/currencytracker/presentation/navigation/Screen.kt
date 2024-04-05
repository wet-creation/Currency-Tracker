package com.mycompany.currencytracker.presentation.navigation

import com.mycompany.currencytracker.common.Constants.CALCULATOR_SCREEN
import com.mycompany.currencytracker.common.Constants.COIN_DETAILS_SCREEN
import com.mycompany.currencytracker.common.Constants.FIAT_DETAILS_SCREEN
import com.mycompany.currencytracker.common.Constants.LOGIN_SCREEN
import com.mycompany.currencytracker.common.Constants.NOTIFICATION_SCREEN
import com.mycompany.currencytracker.common.Constants.PROFILE_SCREEN
import com.mycompany.currencytracker.common.Constants.REGISTER_SCREEN
import com.mycompany.currencytracker.common.Constants.SELECT_MAIN_CURRENCY_SCREEN
import com.mycompany.currencytracker.common.Constants.SETTINGS_SCREEN

sealed class Screen(val route: String){
    object NotificationScreen: Screen(NOTIFICATION_SCREEN)
    object SettingScreen: Screen(SETTINGS_SCREEN)
    object SelectMainCurrencyScreen: Screen(SELECT_MAIN_CURRENCY_SCREEN)
    object CalculatorScreen: Screen(CALCULATOR_SCREEN)
    object CurrencyDetailScreen: Screen(FIAT_DETAILS_SCREEN)
    object CryptoDetailScreen: Screen(COIN_DETAILS_SCREEN)
    object LoginScreen: Screen(LOGIN_SCREEN)
    object RegisterScreen: Screen(REGISTER_SCREEN)
    object ProfileScreen: Screen(PROFILE_SCREEN)
}