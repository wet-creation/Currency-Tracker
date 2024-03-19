package com.mycompany.currencytracker.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mycompany.currencytracker.common.Constants.coin_detail_screen
import com.mycompany.currencytracker.common.Constants.favorite
import com.mycompany.currencytracker.common.Constants.fiat_detail_screen
import com.mycompany.currencytracker.common.Constants.home
import com.mycompany.currencytracker.common.Constants.search
import com.mycompany.currencytracker.common.Constants.setting_screen


@Composable
fun MainScreen() {
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val topBarState = rememberSaveable { (mutableStateOf(true)) }

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    when (navBackStackEntry?.destination?.route) {
        home -> {
            bottomBarState.value = true
            topBarState.value = true
        }

        search -> {
            bottomBarState.value = true
            topBarState.value = false
        }

        favorite -> {
            bottomBarState.value = true
            topBarState.value = true
        }

        setting_screen -> {
            bottomBarState.value = false
            topBarState.value = true
        }

        fiat_detail_screen -> {
            bottomBarState.value = false
            topBarState.value = true
        }

        "$fiat_detail_screen/{currencyId}" -> {
            bottomBarState.value = false
            topBarState.value = true
        }

        "${coin_detail_screen}/{coinId}" -> {
            bottomBarState.value = false
            topBarState.value = true
        }
    }

    Scaffold(
        topBar = {
            TopBar(navController, topBarState)
        },
        bottomBar = {
            BottomBar(navController, bottomBarState)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Navigate(navController = navController)
        }

    }
}