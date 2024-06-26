package com.mycompany.currencytracker.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.presentation.navigation.NavigationRoutes.CALCULATOR_SCREEN
import com.mycompany.currencytracker.presentation.navigation.NavigationRoutes.HOME_SCREEN
import com.mycompany.currencytracker.presentation.navigation.NavigationRoutes.LOGIN_SCREEN
import com.mycompany.currencytracker.presentation.navigation.NavigationRoutes.MY_ACCOUNT_SCREEN
import com.mycompany.currencytracker.presentation.navigation.NavigationRoutes.NOTIFICATION_SCREEN
import com.mycompany.currencytracker.presentation.navigation.NavigationRoutes.NOTIFICATION_SCREEN_LIST
import com.mycompany.currencytracker.presentation.navigation.NavigationRoutes.NOTIFICATION_SCREEN_SELECT
import com.mycompany.currencytracker.presentation.navigation.NavigationRoutes.PROFILE_SCREEN
import com.mycompany.currencytracker.presentation.navigation.NavigationRoutes.REGISTER_SCREEN
import com.mycompany.currencytracker.presentation.navigation.NavigationRoutes.SELECT_MAIN_CURRENCY_SCREEN
import com.mycompany.currencytracker.presentation.navigation.NavigationRoutes.SETTINGS_SCREEN


@Composable
fun TopBar(navHostController: NavHostController, topBarState: MutableState<Boolean>) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()

    val title: String = when (navBackStackEntry?.destination?.route ?: HOME_SCREEN) {
        MY_ACCOUNT_SCREEN -> stringResource(id = R.string.account_top_bar)
        CALCULATOR_SCREEN -> stringResource(id = R.string.converter_top_bar)
        SELECT_MAIN_CURRENCY_SCREEN -> stringResource(id = R.string.select_currency_top_bar)
        PROFILE_SCREEN -> stringResource(id = R.string.profile_screen_title)
        NOTIFICATION_SCREEN_SELECT -> stringResource(id = R.string.add_a_price_allert)
        "$NOTIFICATION_SCREEN/{coinId}" -> stringResource(id = R.string.add_a_price_allert)
        else -> stringResource(id = R.string.app_name)
    }

    AnimatedVisibility(visible = topBarState.value,
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it }),
        content = {
            when (navBackStackEntry?.destination?.route ?: HOME_SCREEN) {
                HOME_SCREEN -> MainTopBar(navHostController = navHostController)
                SETTINGS_SCREEN -> SettingTopBar(navController = navHostController)
                MY_ACCOUNT_SCREEN -> SubTopAppBar(navController = navHostController, title = title)
                CALCULATOR_SCREEN -> SubTopAppBar(navController = navHostController, title = title)
                NOTIFICATION_SCREEN_SELECT -> SubTopAppBar(
                    navController = navHostController,
                    title = title
                )

                "$NOTIFICATION_SCREEN/{coinId}" -> SubTopAppBar(
                    navController = navHostController,
                    title = title
                )

                NOTIFICATION_SCREEN_LIST -> NotificationsListTopBar(navController = navHostController)
                SELECT_MAIN_CURRENCY_SCREEN -> SubTopAppBar(
                    navController = navHostController, title = title
                )

                LOGIN_SCREEN -> AuthTopBar()

                REGISTER_SCREEN -> AuthTopBar()

                PROFILE_SCREEN -> SubTopAppBar(navController = navHostController, title = title)


                else -> MainTopBar(navHostController = navHostController)
            }
        })


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(navHostController: NavHostController) {
    val dataStore = StoreUserSetting(LocalContext.current)
    TopAppBar(modifier = Modifier.padding(horizontal = 10.dp), title = {
        Text(
            "Currency tracker", style = TextStyle(
                fontSize = 24.sp,
                lineHeight = 22.sp,
                fontWeight = FontWeight(600),
                color = MaterialTheme.colorScheme.primary
            )
        )
    }, navigationIcon = {
        Image(
            modifier = Modifier
                .padding(end = 2.dp)
                .width(23.dp)
                .height(23.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo"
        )
    }, actions = {
        IconButton(onClick = {
            if (dataStore.getUser().email.isEmpty())
                navHostController.navigate(Screen.LoginScreen.route)
            else
                navHostController.navigate(Screen.NotificationScreenList.route)
        }) {
            Icon(
                modifier = Modifier
                    .padding(0.03.dp)
                    .width(24.dp)
                    .height(24.dp),
                painter = painterResource(id = R.drawable.notification_icon),
                contentDescription = "notification"
            )
        }
        IconButton(onClick = {
            navHostController.navigate((Screen.SettingScreen.route))
        }) {
            Icon(
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp),
                imageVector = Icons.Outlined.AccountCircle,
                contentDescription = "profile"
            )
        }
    },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background,
            navigationIconContentColor = MaterialTheme.colorScheme.secondary,
            titleContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.secondary
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingTopBar(navController: NavHostController) {
    CenterAlignedTopAppBar(title = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .width(23.dp)
                    .height(23.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo"
            )
            Text(
                text = "Currency Tracker", style = TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight(600),
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
    }, navigationIcon = {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Back"
            )
        }
    },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background,
            navigationIconContentColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsListTopBar(navController: NavHostController) {
    CenterAlignedTopAppBar(title = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.price_allerts), style = TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight(600),
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
    }, navigationIcon = {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Back"
            )
        }
    },
        actions = {
            Icon(
                painter = painterResource(id = R.drawable.add_notification_icon),
                contentDescription = "add notifications",
                modifier = Modifier
                    .padding(end = 20.dp)
                    .clickable { navController.navigate(Screen.NotificationScreenSelect.route) }
            )
        },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background,
            navigationIconContentColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .width(23.dp)
                        .height(23.dp),
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo"
                )
                Text(
                    text = "Currency Tracker", style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 22.sp,
                        fontWeight = FontWeight(600),
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
        },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background,
            navigationIconContentColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubTopAppBar(navController: NavHostController, title: String) {
    TopAppBar(title = {
        Text(
            text = title, style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 22.sp,
                fontWeight = FontWeight(600),
                color = MaterialTheme.colorScheme.primary
            )
        )
    }, navigationIcon = {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Back"
            )
        }
    },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background,
            navigationIconContentColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBarFiat(
    navController: NavHostController,
    title: String,
    followStatus: Boolean,
    onItemClick: () -> Unit
) {

    TopAppBar(title = {
        Text(
            text = title.uppercase(), style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 22.sp,
                fontWeight = FontWeight(600),
                color = MaterialTheme.colorScheme.primary
            )
        )
    }, navigationIcon = {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Back"
            )
        }
    }, actions = {
        IconButton(onClick = { navController.navigate(BottomBarScreen.Search.route) }) {
            Icon(
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp),
                painter = painterResource(id = R.drawable.search_icon),
                contentDescription = "icon"
            )
        }
        IconButton(onClick = { onItemClick() }) {
            if (followStatus) {
                Icon(
                    imageVector = Icons.Filled.Favorite, contentDescription = "favorite",
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp),
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "favorite",
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp),
                )
            }

        }
    },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background,
            navigationIconContentColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar(
    navController: NavHostController,
    title: String,
    followStatus: Boolean,
    onItemClick: () -> Unit,
    onNotificationClick: () -> Unit
) {

    TopAppBar(title = {
        Text(
            text = title.uppercase(), style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 22.sp,
                fontWeight = FontWeight(600),
                color = MaterialTheme.colorScheme.primary
            )
        )
    }, navigationIcon = {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Back"
            )
        }
    }, actions = {
        IconButton(onClick = { navController.navigate(BottomBarScreen.Search.route) }) {
            Icon(
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp),
                painter = painterResource(id = R.drawable.search_icon),
                contentDescription = "icon"
            )
        }
        IconButton(onClick = {
            onNotificationClick()
        }) {
            Icon(
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp),
                painter = painterResource(id = R.drawable.notification_icon),
                contentDescription = "notification"
            )
        }
        IconButton(onClick = { onItemClick() }) {
            if (followStatus) {
                Icon(
                    imageVector = Icons.Filled.Favorite, contentDescription = "favorite",
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp),
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "favorite",
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp),
                )
            }

        }
    },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background,
            navigationIconContentColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary
        )
    )
}
