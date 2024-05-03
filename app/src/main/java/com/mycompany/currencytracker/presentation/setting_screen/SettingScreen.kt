package com.mycompany.currencytracker.presentation.setting_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.presentation.navigation.Screen
import com.mycompany.currencytracker.presentation.ui.theme.mainTextColor

@Composable
fun SettingScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        val dataStore = StoreUserSetting(LocalContext.current)

        Row(
            modifier = Modifier
                .padding(start = 18.dp, top = 16.dp, bottom = 16.dp)
                .clickable {
                    if (dataStore.getUser().email.isEmpty()) {
                        navController.navigate(Screen.LoginScreen.route)
                    } else
                        navController.navigate(Screen.ProfileScreen.route)
                }

        ) {
            Text(
                text = stringResource(id = R.string.setting_my_account),
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight(400),
                    color = mainTextColor
                )
            )
        }
        HorizontalDivider()
        Row(
            modifier = Modifier.padding(start = 18.dp, top = 16.dp, bottom = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.setting_dark_mode),
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight(400),
                    color = mainTextColor
                )
            )
        }
        HorizontalDivider()
        Row(
            modifier = Modifier
                .padding(start = 18.dp, top = 16.dp, bottom = 16.dp)
                .clickable(onClick = { navController.navigate(Screen.SelectMainCurrencyScreen.route) })
        ) {
            Text(
                text = stringResource(id = R.string.setting_currency),
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight(400),
                    color = mainTextColor
                ),
            )
        }
        HorizontalDivider()
        Row(
            modifier = Modifier.padding(start = 18.dp, top = 16.dp, bottom = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.setting_language),
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight(400),
                    color = mainTextColor
                )
            )
        }
        HorizontalDivider()
        Row(
            modifier = Modifier
                .padding(start = 20.dp, top = 15.dp)
                .clickable(onClick = { navController.navigate(Screen.CalculatorScreen.route) })
        ) {
            Icon(
                modifier = Modifier.padding(end = 4.dp),
                painter = painterResource(id = R.drawable.calculator_icon),
                contentDescription = "notification",
                tint = Color(0xFFFFFFFF)
            )
            Text(
                text = stringResource(id = R.string.calculator),
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight(400),
                    color = mainTextColor
                )
            )
        }
        Row(
            modifier = Modifier
                .padding(start = 20.dp, top = 20.dp)
                .clickable(onClick = {
                    if (dataStore.getUser().email.isNotBlank()) navController.navigate(
                        Screen.NotificationScreenList.route
                    ) else navController.navigate(Screen.LoginScreen.route)
                })
        ) {
            Icon(
                modifier = Modifier.padding(end = 4.dp),
                painter = painterResource(id = R.drawable.notification_icon),
                contentDescription = "notification",
                tint = Color(0xFFFFFFFF)
            )
            Text(
                text = stringResource(id = R.string.notification),
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight(400),
                    color = mainTextColor
                )
            )
        }
    }
}