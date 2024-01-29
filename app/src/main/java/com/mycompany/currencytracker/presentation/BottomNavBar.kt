package com.mycompany.currencytracker.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.presentation.currency_list.CurrencyListScreen
import com.mycompany.currencytracker.presentation.fav_list.FavoriteListScreen
import com.mycompany.currencytracker.presentation.seacrh.SearchScreen
import com.mycompany.currencytracker.presentation.ui.theme.secondBackColor
import com.mycompany.currencytracker.presentation.ui.theme.secondTextColor
import com.mycompany.currencytracker.presentation.ui.theme.selectTextColor


@Composable
fun BottomNavGraph(navController: NavHostController) {
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
    }
}


@Composable
fun BottomBar(navHostController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Search,
        BottomBarScreen.Favorite
    )
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Row(
        modifier = Modifier
            .width(390.dp)
            .height(80.dp)
            .background(color = secondBackColor, shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navHostController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

    val contentColor =
        if (selected) selectTextColor else secondTextColor

    Box(
        modifier = Modifier
            .width(47.dp)
            .height(62.dp)
            .padding(top = 2.dp)
            .clickable(onClick = {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            })
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier
                    .padding(0.17778.dp)
                    .width(40.dp)
                    .height(40.dp),
                painter = painterResource(id = if (selected) screen.selectedIcon else screen.unselectedIcon),
                contentDescription = "icon"
            )

            Text(
                text = screen.title,
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight(600),
                    color = contentColor
                )
            )
        }
    }
}