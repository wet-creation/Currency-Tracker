package com.mycompany.currencytracker.presentation

import android.content.res.Resources
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.common.Constants

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int
) {
    object Home: BottomBarScreen(
        route = Constants.home,
        //title = Resources.getSystem().getString(R.string.home_screen_name),
        title = "Home",
        selectedIcon  = R.drawable.home_selected,
        unselectedIcon = R.drawable.home_unselected
    )
    object Search: BottomBarScreen(
        route = Constants.search,
        //title = Resources.getSystem().getString(R.string.search_screen_name),
        title = "Search",
        selectedIcon = R.drawable.search_selected,
        unselectedIcon = R.drawable.search_unselected
    )
    object Favorite: BottomBarScreen(
        route = Constants.favorite,
        //title = Resources.getSystem().getString(R.string.favorite_screen_name),
        title = "Favorite",
        selectedIcon = R.drawable.favorite_selected,
        unselectedIcon = R.drawable.favorite_unselected
    )
}