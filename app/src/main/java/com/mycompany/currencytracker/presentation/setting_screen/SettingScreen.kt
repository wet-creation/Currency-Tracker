package com.mycompany.currencytracker.presentation.setting_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
import com.mycompany.currencytracker.data.datastore.Theme
import com.mycompany.currencytracker.presentation.changeLocales
import com.mycompany.currencytracker.presentation.navigation.Screen

@Composable
fun SettingScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        val context = LocalContext.current
        val dataStore = StoreUserSetting(context)

        var showPopupMenuTheme by remember {
            mutableStateOf(false)
        }

        var showPopupMenuLanguage by remember {
            mutableStateOf(false)
        }

        val themeId = if (dataStore.getTheme() == Theme.DARK){
            R.string.theme_dark
        } else if (dataStore.getTheme() == Theme.LIGHT){
            R.string.theme_light
        } else {
            R.string.theme_system
        }

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
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
        HorizontalDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 18.dp, top = 16.dp, bottom = 16.dp, end = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = stringResource(id = R.string.theme_text),
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight(400),
                    color = MaterialTheme.colorScheme.primary
                )
            )

            Box(){
                Text(
                    text = stringResource(
                        id = themeId
                    ),
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        fontWeight = FontWeight(400),
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier.clickable {
                        showPopupMenuTheme = true
                    }
                )

                DropdownMenu(expanded = showPopupMenuTheme, onDismissRequest = { showPopupMenuTheme = false }) {
                    DropdownMenuItem(text = { Text(text = stringResource(id = R.string.theme_system)) }, onClick = {
                        showPopupMenuTheme = false
                        dataStore.saveTheme(Theme.SYSTEM)
                    })
                    DropdownMenuItem(text = { Text(text = stringResource(id = R.string.theme_dark)) }, onClick = {
                        showPopupMenuTheme = false
                        dataStore.saveTheme(Theme.DARK)
                    })
                    DropdownMenuItem(text = { Text(text = stringResource(id = R.string.theme_light)) }, onClick = {
                        showPopupMenuTheme = false
                        dataStore.saveTheme(Theme.LIGHT)
                    })
                }
            }

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
                    color = MaterialTheme.colorScheme.primary
                ),
            )
        }
        HorizontalDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 18.dp, top = 16.dp, bottom = 16.dp, end = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.setting_language),
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight(400),
                    color = MaterialTheme.colorScheme.primary
                )
            )

            Box(){
                Text(
                    text = stringResource(
                        id = R.string.selLanguage
                    ),
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        fontWeight = FontWeight(400),
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier.clickable {
                        showPopupMenuLanguage = true
                    }
                )

                DropdownMenu(expanded = showPopupMenuLanguage, onDismissRequest = { showPopupMenuLanguage = false }) {
                    DropdownMenuItem(text = { Text(text = "Українська") }, onClick = {
                        changeLocales(context, "uk")
                        showPopupMenuLanguage = false
                    })
                    DropdownMenuItem(text = { Text(text = "English") }, onClick = {
                        changeLocales(context, "en")
                        showPopupMenuLanguage = false
                    })
                }
            }
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
                tint = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = stringResource(id = R.string.calculator),
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight(400),
                    color = MaterialTheme.colorScheme.primary
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
                tint = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = stringResource(id = R.string.notification),
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight(400),
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}