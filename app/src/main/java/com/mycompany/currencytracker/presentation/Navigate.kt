package com.mycompany.currencytracker.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mycompany.currencytracker.presentation.calculator.ui.CalculatorScreen
import com.mycompany.currencytracker.presentation.fav_list.FavoriteListScreen
import com.mycompany.currencytracker.presentation.notification_screen.NotificatiionScreen
import com.mycompany.currencytracker.presentation.seacrh.SearchScreen
import com.mycompany.currencytracker.presentation.setting_screen.SettingScreen
import com.mycompany.currencytracker.presentation.calculator.currencySelectScreen.CurrencySelectScreen

@Composable
fun Navigate(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(BottomBarScreen.Home.route) {
            MainListScreen()
        }
        composable(BottomBarScreen.Search.route) {
            SearchScreen()
        }
        composable(BottomBarScreen.Favorite.route) {
            FavoriteListScreen()
        }
        composable(Screen.NotificationScreen.route){
            NotificatiionScreen()
        }
        composable(Screen.SettingScreen.route){
            SettingScreen(navController = navController)
        }
        composable(Screen.SelectMainCurrencyScreen.route){
            CurrencySelectScreen()
        }
        composable(Screen.CalculatorScreen.route){
            CalculatorScreen()
        }
    }
}