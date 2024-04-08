package com.mycompany.currencytracker.presentation.navigation

import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.common.Constants

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int
) {
    object Home: BottomBarScreen(
        route = Constants.HOME_SCREEN,
        //title = Resources.getSystem().getString(R.string.home_screen_name),
        title = "Home",
        icon = R.drawable.home_icon
    )
    object Search: BottomBarScreen(
        route = Constants.SEARCH_SCREEN,
        //title = Resources.getSystem().getString(R.string.search_screen_name),
        title = "Search",
        icon = R.drawable.search_icon
    )
    object Favorite: BottomBarScreen(
        route = Constants.FAVORITE_SCREEN,
        //title = Resources.getSystem().getString(R.string.favorite_screen_name),
        title = "Favorite",
        icon = R.drawable.favorite_icon
    )
}