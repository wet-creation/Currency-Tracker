package com.mycompany.currencytracker.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mycompany.currencytracker.presentation.MainListScreen
import com.mycompany.currencytracker.presentation.auth.login.LoginScreen
import com.mycompany.currencytracker.presentation.auth.register.RegisterScreen
import com.mycompany.currencytracker.presentation.calculator.ui.CalculatorScreen
import com.mycompany.currencytracker.presentation.crypto_detail.CryptoDetailScreen
import com.mycompany.currencytracker.presentation.currency_detail.CurrencyDetailScreen
import com.mycompany.currencytracker.presentation.fav_list.FavoriteListScreen
import com.mycompany.currencytracker.presentation.notification_screen.NotificatiionScreen
import com.mycompany.currencytracker.presentation.profile.ProfileScreen
import com.mycompany.currencytracker.presentation.seacrh.SearchScreen
import com.mycompany.currencytracker.presentation.setting_screen.SettingScreen
import com.mycompany.currencytracker.presentation.setting_screen.currency_select_screen.CurrencySelectScreen

@Composable
fun Navigate(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(BottomBarScreen.Home.route) {
            MainListScreen(navController = navController)
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
        composable(Screen.RegisterScreen.route){
            RegisterScreen(navController)
        }
        composable(Screen.ProfileScreen.route){
            ProfileScreen(navController)
        }
        composable(Screen.LoginScreen.route){
            LoginScreen(navController)
        }
        composable(Screen.CalculatorScreen.route){
            CalculatorScreen()
        }
        composable(Screen.CurrencyDetailScreen.route + "/{currencyId}"){
            CurrencyDetailScreen()
        }
        composable(Screen.CryptoDetailScreen.route + "/{coinId}"){
            CryptoDetailScreen()
        }
    }
}