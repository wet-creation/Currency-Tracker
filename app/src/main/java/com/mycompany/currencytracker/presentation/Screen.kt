package com.mycompany.currencytracker.presentation

import com.mycompany.currencytracker.common.Constants.calculator_screen
import com.mycompany.currencytracker.common.Constants.coin_detail_screen
import com.mycompany.currencytracker.common.Constants.fiat_detail_screen
import com.mycompany.currencytracker.common.Constants.notification_screen
import com.mycompany.currencytracker.common.Constants.select_main_currency_screen
import com.mycompany.currencytracker.common.Constants.setting_screen

sealed class Screen(val route: String){
    object NotificationScreen: Screen(notification_screen)
    object SettingScreen: Screen(setting_screen)
    object SelectMainCurrencyScreen: Screen(select_main_currency_screen)
    object CalculatorScreen: Screen(calculator_screen)
    object CurrencyDetailScreen: Screen(fiat_detail_screen)
    object CryptoDetailScreen: Screen(coin_detail_screen)
}