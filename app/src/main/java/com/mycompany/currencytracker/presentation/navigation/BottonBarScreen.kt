package com.mycompany.currencytracker.presentation.navigation

import com.mycompany.currencytracker.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int
) {
    object Home: BottomBarScreen(
        route = NavigationRoutes.HOME_SCREEN,
        title = "Home",
        icon = R.drawable.home_icon
    )
    object Search: BottomBarScreen(
        route = NavigationRoutes.SEARCH_SCREEN,
        title = "Search",
        icon = R.drawable.search_icon
    )
    object Favorite: BottomBarScreen(
        route = NavigationRoutes.FAVORITE_SCREEN,
        title = "Favorite",
        icon = R.drawable.favorite_icon
    )
}