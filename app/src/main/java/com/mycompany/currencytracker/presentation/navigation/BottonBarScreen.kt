package com.mycompany.currencytracker.presentation.navigation

import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.presentation.common.UiText

sealed class BottomBarScreen(
    val route: String,
    val title: UiText,
    val icon: Int
) {

    object Home: BottomBarScreen(
        route = NavigationRoutes.HOME_SCREEN,
        title = UiText.StringResource(R.string.home_screen_name),
        icon = R.drawable.home_icon,
    )
    object Search: BottomBarScreen(
        route = NavigationRoutes.SEARCH_SCREEN,
        title = UiText.StringResource(R.string.search_screen_name),
        icon = R.drawable.search_icon
    )
    object Favorite: BottomBarScreen(
        route = NavigationRoutes.FAVORITE_SCREEN,
        title = UiText.StringResource(R.string.favorite_screen_name),
        icon = R.drawable.favorite_icon
    )
}