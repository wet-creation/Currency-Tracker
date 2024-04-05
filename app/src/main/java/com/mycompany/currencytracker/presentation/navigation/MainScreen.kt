package com.mycompany.currencytracker.presentation.navigation

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
import com.mycompany.currencytracker.common.Constants.COIN_DETAILS_SCREEN
import com.mycompany.currencytracker.common.Constants.FAVORITE_SCREEN
import com.mycompany.currencytracker.common.Constants.FIAT_DETAILS_SCREEN
import com.mycompany.currencytracker.common.Constants.HOME_SCREEN
import com.mycompany.currencytracker.common.Constants.LOGIN_SCREEN
import com.mycompany.currencytracker.common.Constants.REGISTER_SCREEN
import com.mycompany.currencytracker.common.Constants.SEARCH_SCREEN
import com.mycompany.currencytracker.common.Constants.SETTINGS_SCREEN


@Composable
fun MainScreen() {
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val topBarState = rememberSaveable { (mutableStateOf(true)) }

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    when (navBackStackEntry?.destination?.route) {
        HOME_SCREEN -> {
            bottomBarState.value = true
            topBarState.value = true
        }

        SEARCH_SCREEN -> {
            bottomBarState.value = true
            topBarState.value = false
        }

        FAVORITE_SCREEN -> {
            bottomBarState.value = true
            topBarState.value = true
        }

        SETTINGS_SCREEN -> {
            bottomBarState.value = false
            topBarState.value = true
        }

        FIAT_DETAILS_SCREEN -> {
            bottomBarState.value = false
            topBarState.value = true
        }

        REGISTER_SCREEN -> {
            bottomBarState.value = false
            topBarState.value = true
        }

        LOGIN_SCREEN -> {
            bottomBarState.value = false
            topBarState.value = true
        }

        "$FIAT_DETAILS_SCREEN/{currencyId}" -> {
            bottomBarState.value = false
            topBarState.value = true
        }

        "${COIN_DETAILS_SCREEN}/{coinId}" -> {
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